package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.shop_backend.dto.request.ColorCreateRequest;
import uz.salikhdev.shop_backend.dto.request.ColorUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.ColorResponse;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.service.ColorService;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
@Tag(name = "Color")
public class ColorController {

    private final ColorService categoryService;

    @GetMapping
    public ResponseEntity<@NonNull List<ColorResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull ColorResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull SuccessResponse> create(@RequestBody @Valid ColorCreateRequest request) {
        categoryService.create(request);
        return SuccessResponse.ok("Color created successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull SuccessResponse> delete(@PathVariable String id) {
        categoryService.delete(id);
        return SuccessResponse.noContent("Color deleted successfully");
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull SuccessResponse> update(@PathVariable String id, @RequestBody ColorUpdateRequest request) {
        categoryService.update(id, request);
        return SuccessResponse.ok("Color updated successfully");
    }

}
