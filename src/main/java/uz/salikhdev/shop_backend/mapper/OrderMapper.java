package uz.salikhdev.shop_backend.mapper;

import org.mapstruct.Mapper;
import uz.salikhdev.shop_backend.dto.response.OrderResponse;
import uz.salikhdev.shop_backend.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponse(List<Order> orders);

}
