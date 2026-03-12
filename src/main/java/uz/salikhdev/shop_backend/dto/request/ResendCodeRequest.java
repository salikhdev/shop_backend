package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ResendCodeRequest(
        @Email(message = "Invalid email format")
        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email is required")
        String email
) {
}
