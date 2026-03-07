package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.shop_backend.dto.request.CategoryCreateRequest;
import uz.salikhdev.shop_backend.dto.request.CategoryUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.CategoryResponse;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<@NonNull List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull CategoryResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<@NonNull SuccessResponse> create(@RequestBody @Valid CategoryCreateRequest request) {
        categoryService.create(request);
        return SuccessResponse.ok("Category created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull SuccessResponse> delete(@PathVariable String id) {
        categoryService.delete(id);
        return SuccessResponse.noContent("Category deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull SuccessResponse> update(@PathVariable String id, @RequestBody CategoryUpdateRequest request) {
        categoryService.update(id, request);
        return SuccessResponse.ok("Category updated successfully");
    }

}
