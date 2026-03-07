package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.salikhdev.shop_backend.dto.response.FileUploadResponse;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.service.MinIoService;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Files")
public class FileController {

    private final MinIoService minIoService;

    @PostMapping(path = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) {
        String key = minIoService.uploadFile(file, "products");
        return ResponseEntity.ok(FileUploadResponse.builder().url(key).build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<@NonNull SuccessResponse> deleteFile(@RequestParam String key) {
        minIoService.deleteFile(key);
        return SuccessResponse.noContent("File deleted successfully");
    }


}
