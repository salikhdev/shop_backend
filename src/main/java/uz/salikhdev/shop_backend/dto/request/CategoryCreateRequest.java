package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryCreateRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Icon is required")
        @NotBlank(message = "Icon cannot be blank")
        String icon
) {
}
