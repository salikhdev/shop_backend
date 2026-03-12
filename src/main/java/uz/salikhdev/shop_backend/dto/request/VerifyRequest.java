package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record VerifyRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        @NotNull(message = "Email cannot be null")
        String email,
        @NotNull
        @Min(value = 1000, message = "Code must be at least 1000")
        @Max(value = 9999, message = "Code must be at most 9999")
        Integer code
) {
}