package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SizeResponse(
        String id,
        String name,
        LocalDateTime createdAt) {
}
