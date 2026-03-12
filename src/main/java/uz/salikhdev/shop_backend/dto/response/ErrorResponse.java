package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        int code
) {

    public static ErrorResponse of(String message, int code) {
        return ErrorResponse.builder()
                .message(message)
                .code(code)
                .build();
    }

}
