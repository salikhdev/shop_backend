package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRegisterRequest(
        @NotBlank(message = "First name is required")
        @NotNull(message = "First name cannot be null")
        String firstName,
        String lastName,
        @NotBlank(message = "Email is required")
        @NotNull(message = "Email cannot be null")
        String email,
        @NotBlank(message = "Password is required")
        @NotNull(message = "Password cannot be null")
        String password
) {
}
