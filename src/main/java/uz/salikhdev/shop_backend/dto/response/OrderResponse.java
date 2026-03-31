package uz.salikhdev.shop_backend.dto.response;

import lombok.Builder;
import uz.salikhdev.shop_backend.entity.Order;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        String id,
        ProductResponse product,
        int quantity,
        BigDecimal totalPrice,
        Order.Status status
) {
}
