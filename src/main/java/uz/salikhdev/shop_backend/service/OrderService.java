package uz.salikhdev.shop_backend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.shop_backend.dto.request.OrderCreateRequest;
import uz.salikhdev.shop_backend.dto.response.OrderResponse;
import uz.salikhdev.shop_backend.entity.Order;
import uz.salikhdev.shop_backend.entity.Product;
import uz.salikhdev.shop_backend.entity.User;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.mapper.OrderMapper;
import uz.salikhdev.shop_backend.repository.OrderRepository;
import uz.salikhdev.shop_backend.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Transactional
    public void createOrder(OrderCreateRequest request, User user) {

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

        product.setQuantity(product.getQuantity() - request.quantity());
        productRepository.save(product);
    }

    public void cancelOrder(String orderId, User user) {


        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not fount"));

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new RuntimeException("this is not your order");
        }

        order.getProduct().setQuantity(order.getQuantity()+ order.getProduct().getQuantity());

        order.setStatus(Order.Status.CANCELED);
        orderRepository.save(order);
    }

    public List<OrderResponse> getMyOrders(User user) {
        List<Order> allOrder = orderRepository.findAllByUser_Id(user.getId());
        return orderMapper.toResponse(allOrder);
    }

    public void editQuantity(String orderId, int quantity, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new RuntimeException("This is not your order");
        }

        Product product = order.getProduct();

        if (product.getQuantity() + order.getQuantity() < quantity) {
            throw new NotFoundException("Not enough product in stock");
        }

        product.setQuantity(product.getQuantity() + order.getQuantity() - quantity);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        productRepository.save(product);
        orderRepository.save(order);
    }

}
