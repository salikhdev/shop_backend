package uz.salikhdev.shop_backend.service;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import uz.salikhdev.shop_backend.dto.request.OrderCreateRequest;
import uz.salikhdev.shop_backend.dto.response.ProductResponse;
import uz.salikhdev.shop_backend.entity.Order;
import uz.salikhdev.shop_backend.entity.Product;
import uz.salikhdev.shop_backend.entity.User;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.repository.OrderRepository;
import uz.salikhdev.shop_backend.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public void createOrder(OrderCreateRequest request , User user) {

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (!(product.getQuantity() >= request.quantity())) {
            throw new NotFoundException("Product soni yetmaydi");
        }

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(request.quantity());
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.quantity())));
        order.setStatus(Order.Status.ACTIVE);
        orderRepository.save(order);
    }

    public void cancelOrder(String orderId , User user) {

       Order order = orderRepository.findById(orderId)
                .orElseThrow( ()-> new NotFoundException("Order not fount"));

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new RuntimeException("this is not your order");
        }

        order.setStatus(Order.Status.CANCELED);
        orderRepository.save(order);
    }

}
