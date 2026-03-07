package uz.salikhdev.shop_backend.dto.request;

import lombok.Builder;

@Builder
public record SizeUpdateRequest(
        String name
) {
}
