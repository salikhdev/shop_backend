package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ProductResponse(
        String id,
        String title,
        String description,
        String image,
        BigDecimal price,
        Integer quantity,
        CategoryResponse category,
        Set<ColorResponse> colors,
        Set<SizeResponse> sizes,
        LocalDateTime createdAt
) {
}
