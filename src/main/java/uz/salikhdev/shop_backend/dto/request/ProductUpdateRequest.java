package uz.salikhdev.shop_backend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record ProductUpdateRequest(
        String title,

        String description,

        String image,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @Min(value = 0, message = "Quantity must be 0 or greater")
        Integer quantity,

        String categoryId,

        Set<String> colorIds,

        Set<String> sizeIds
) {
}
