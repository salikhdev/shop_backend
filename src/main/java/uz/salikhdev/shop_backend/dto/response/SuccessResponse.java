package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

@Builder
public record SuccessResponse(
        String message,
        int code
) {

    public static ResponseEntity<@NonNull SuccessResponse> ok(String message) {
        return ResponseEntity.ok(
                SuccessResponse.builder()
                        .message(message)
                        .code(200)
                        .build()
        );
    }

    public static ResponseEntity<@NonNull SuccessResponse> noContent(String message) {
        return ResponseEntity.status(204).body(
                SuccessResponse.builder()
                        .message(message)
                        .code(204)
                        .build()
        );
    }

}
