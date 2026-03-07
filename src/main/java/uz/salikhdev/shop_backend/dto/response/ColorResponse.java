package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ColorResponse(
        String id,
        String name,
        String hex,
        LocalDateTime createdAt) {
}
