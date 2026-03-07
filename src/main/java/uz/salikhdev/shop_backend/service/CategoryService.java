package uz.salikhdev.shop_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.salikhdev.shop_backend.dto.request.CategoryCreateRequest;
import uz.salikhdev.shop_backend.dto.request.CategoryUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.CategoryResponse;
import uz.salikhdev.shop_backend.entity.Category;
import uz.salikhdev.shop_backend.exception.AlreadyExistsException;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.mapper.CategoryMapper;
import uz.salikhdev.shop_backend.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void create(CategoryCreateRequest request) {
        if (categoryRepository.existsByNameAndDeletedAtIsNull(request.name())) {
            throw new AlreadyExistsException("Category with name " + request.name() + " already exists");
        }

        if (categoryRepository.existsByName(request.name())) {
            Category deletedCategory = categoryRepository.findByName(request.name())
                    .orElseThrow(() -> new NotFoundException("Deleted category not found with name: " + request.name()));
            deletedCategory.setDeletedAt(null);
            categoryRepository.save(deletedCategory);
            return;
        }

        Category category = categoryMapper.toEntity(request);
        categoryRepository.save(category);
    }

    public void update(String id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        if (request.name() != null) {
            if (!category.getName().equals(request.name()) && categoryRepository.existsByNameAndDeletedAtIsNull(request.name())) {
                throw new AlreadyExistsException("Category with name " + request.name() + " already exists");
            }
            category.setName(request.name());
        }

        if (request.icon() != null) {
            category.setIcon(request.icon());
        }

        categoryRepository.save(category);
    }

    public void delete(String id) {
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    public CategoryResponse getById(String id) {
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAllByDeletedAtIsNull();
        return categoryMapper.toResponse(categories);
    }

}
