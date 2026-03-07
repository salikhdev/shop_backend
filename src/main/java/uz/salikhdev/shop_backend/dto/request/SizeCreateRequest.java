package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SizeCreateRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        String name
) {
}
