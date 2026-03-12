package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String token
) {
}
