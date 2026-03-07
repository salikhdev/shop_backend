package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.shop_backend.dto.request.SizeCreateRequest;
import uz.salikhdev.shop_backend.dto.request.SizeUpdateRequest;
import uz.salikhdev.shop_backend.dto.response.SizeResponse;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.service.SizeService;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
@Tag(name = "Sizes")
public class SizeController {

    private final SizeService sizeService;

    @GetMapping
    public ResponseEntity<@NonNull List<SizeResponse>> getAll() {
        return ResponseEntity.ok(sizeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull SizeResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(sizeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<@NonNull SuccessResponse> create(@RequestBody @Valid SizeCreateRequest request) {
        sizeService.create(request);
        return SuccessResponse.ok("Size created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull SuccessResponse> delete(@PathVariable String id) {
        sizeService.delete(id);
        return SuccessResponse.noContent("Size deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull SuccessResponse> update(@PathVariable String id, @RequestBody SizeUpdateRequest request) {
        sizeService.update(id, request);
        return SuccessResponse.ok("Size updated successfully");
    }

}
