package uz.salikhdev.shop_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.salikhdev.shop_backend.dto.request.ProductCreateRequest;
import uz.salikhdev.shop_backend.dto.request.ProductUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.ProductResponse;
import uz.salikhdev.shop_backend.entity.Category;
import uz.salikhdev.shop_backend.entity.Color;
import uz.salikhdev.shop_backend.entity.Product;
import uz.salikhdev.shop_backend.entity.Size;
import uz.salikhdev.shop_backend.exception.AlreadyExistsException;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.mapper.ProductMapper;
import uz.salikhdev.shop_backend.repository.CategoryRepository;
import uz.salikhdev.shop_backend.repository.ColorRepository;
import uz.salikhdev.shop_backend.repository.ProductRepository;
import uz.salikhdev.shop_backend.repository.SizeRepository;
import uz.salikhdev.shop_backend.repository.spec.ProductSpecifications;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductMapper productMapper;

    @Transactional
    public void create(ProductCreateRequest request) {
        if (productRepository.existsByTitleAndDeletedAtIsNull(request.title())) {
            throw new AlreadyExistsException("Product with title '" + request.title() + "' already exists");
        }

        Optional<Product> existingByTitle = productRepository.findByTitle(request.title());
        if (existingByTitle.isPresent() && existingByTitle.get().getDeletedAt() != null) {
            restoreDeletedProduct(existingByTitle.get(), request);
            return;
        }

        Category category = categoryRepository.findByIdAndDeletedAtIsNull(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + request.categoryId()));

        Set<Color> colors = resolveColors(request.colorIds());
        Set<Size> sizes = resolveSizes(request.sizeIds());

        Product product = Product.builder()
                .title(request.title())
                .description(request.description())
                .image(request.image())
                .price(request.price())
                .quantity(request.quantity())
                .category(category)
                .colors(colors)
                .sizes(sizes)
                .build();

        productRepository.save(product);
    }

    @Transactional
    public void update(String id, ProductUpdateRequest request) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        if (request.title() != null) {
            if (!product.getTitle().equals(request.title()) && productRepository.existsByTitleAndDeletedAtIsNull(request.title())) {
                throw new AlreadyExistsException("Product with title '" + request.title() + "' already exists");
            }
            product.setTitle(request.title());
        }

        if (request.description() != null) {
            product.setDescription(request.description());
        }

        if (request.image() != null) {
            product.setImage(request.image());
        }

        if (request.price() != null) {
            product.setPrice(request.price());
        }

        if (request.quantity() != null) {
            product.setQuantity(request.quantity());
        }

        if (request.categoryId() != null) {
            Category category = categoryRepository.findByIdAndDeletedAtIsNull(request.categoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with id: " + request.categoryId()));
            product.setCategory(category);
        }

        if (request.colorIds() != null) {
            product.setColors(resolveColors(request.colorIds()));
        }

        if (request.sizeIds() != null) {
            product.setSizes(resolveSizes(request.sizeIds()));
        }

        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    @Transactional
    public void delete(String id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public ProductResponse getById(String id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    public List<ProductResponse> getAll() {
        return productMapper.toResponse(productRepository.findAllByDeletedAtIsNull());
    }

    public List<ProductResponse> getAllByCategory(String categoryId) {
        categoryRepository.findByIdAndDeletedAtIsNull(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + categoryId));
        return productMapper.toResponse(productRepository.findAllByCategoryIdAndDeletedAtIsNull(categoryId));
    }

    public Page<ProductResponse> search(
            String title,
            String categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = normalizePageSize(size);
        Sort sort = Sort.by(parseSortDirection(sortDir), normalizeSortBy(sortBy));
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);

        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            BigDecimal temp = minPrice;
            minPrice = maxPrice;
            maxPrice = temp;
        }

        String normalizedTitle = (title == null || title.isBlank()) ? null : title.trim();

        Specification<Product> spec = Specification.where(ProductSpecifications.notDeleted())
                .and(ProductSpecifications.titleContains(normalizedTitle))
                .and(ProductSpecifications.categoryId(categoryId))
                .and(ProductSpecifications.minPrice(minPrice))
                .and(ProductSpecifications.maxPrice(maxPrice))
                .and(ProductSpecifications.inStock(inStock));

        return productRepository.findAll(spec, pageable)
                .map(productMapper::toResponse);
    }

    private void restoreDeletedProduct(Product product, ProductCreateRequest request) {
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + request.categoryId()));

        Set<Color> colors = resolveColors(request.colorIds());
        Set<Size> sizes = resolveSizes(request.sizeIds());

        product.setTitle(request.title());
        product.setDescription(request.description());
        product.setImage(request.image());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setCategory(category);
        product.setColors(colors);
        product.setSizes(sizes);
        product.setDeletedAt(null);
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    private int normalizePageSize(int size) {
        if (size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return Math.min(size, MAX_PAGE_SIZE);
    }

    private Sort.Direction parseSortDirection(String sortDir) {
        if (sortDir == null) {
            return Sort.Direction.DESC;
        }
        return "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    private String normalizeSortBy(String sortBy) {
        if (sortBy == null) {
            return "createdAt";
        }
        return switch (sortBy) {
            case "title", "price", "quantity", "createdAt" -> sortBy;
            default -> "createdAt";
        };
    }

    private Set<Color> resolveColors(Set<String> colorIds) {
        if (colorIds == null || colorIds.isEmpty()) return new HashSet<>();
        Set<Color> colors = new HashSet<>();
        for (String colorId : colorIds) {
            Color color = colorRepository.findByIdAndDeletedAtIsNull(colorId)
                    .orElseThrow(() -> new NotFoundException("Color not found with id: " + colorId));
            colors.add(color);
        }
        return colors;
    }

    private Set<Size> resolveSizes(Set<String> sizeIds) {
        if (sizeIds == null || sizeIds.isEmpty()) return new HashSet<>();
        Set<Size> sizes = new HashSet<>();
        for (String sizeId : sizeIds) {
            Size size = sizeRepository.findByIdAndDeletedAtIsNull(sizeId)
                    .orElseThrow(() -> new NotFoundException("Size not found with id: " + sizeId));
            sizes.add(size);
        }
        return sizes;
    }
}
