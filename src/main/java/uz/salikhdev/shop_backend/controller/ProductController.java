package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.shop_backend.dto.request.ProductCreateRequest;
import uz.salikhdev.shop_backend.dto.request.ProductUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.ProductResponse;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<@NonNull List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<@NonNull Page<ProductResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ResponseEntity.ok(
                productService.search(title, categoryId, minPrice, maxPrice, inStock, page, size, sortBy, sortDir)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull ProductResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<@NonNull List<ProductResponse>> getAllByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(productService.getAllByCategory(categoryId));
    }

    // ADMIN

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull SuccessResponse> create(@RequestBody @Valid ProductCreateRequest request) {
        productService.create(request);
        return SuccessResponse.ok("Product created successfully");
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull SuccessResponse> update(@PathVariable String id, @RequestBody @Valid ProductUpdateRequest request) {
        productService.update(id, request);
        return SuccessResponse.ok("Product updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull SuccessResponse> delete(@PathVariable String id) {
        productService.delete(id);
        return SuccessResponse.noContent("Product deleted successfully");
    }
}
