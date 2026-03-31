package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record ProductCreateRequest(
        @NotNull(message = "Title is required")
        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotNull(message = "Description is required")
        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Image is required")
        @NotBlank(message = "Image cannot be blank")
        String image,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity must be 0 or greater")
        Integer quantity,

        @NotNull(message = "Category ID is required")
        String categoryId,

        Set<String> colorIds,

        Set<String> sizeIds
) {
}
