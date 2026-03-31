package uz.salikhdev.shop_backend.dto.request;

import lombok.Builder;

@Builder
public record OrderCreateRequest(
        String productId,
        Integer quantity
) {
}
