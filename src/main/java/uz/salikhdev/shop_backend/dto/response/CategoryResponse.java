package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoryResponse(
        String id,
        String name,
        String icon,
        LocalDateTime createdAt) {
}
