package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ColorCreateRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Hex is required")
        @NotBlank(message = "Hex cannot be blank")
        String hex
) {
}
