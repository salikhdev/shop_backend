package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.shop_backend.dto.request.OrderCreateRequest;
import uz.salikhdev.shop_backend.dto.response.OrderResponse;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.entity.User;
import uz.salikhdev.shop_backend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<@NonNull SuccessResponse> createOrder(@RequestBody OrderCreateRequest request, @AuthenticationPrincipal User user) {
        orderService.createOrder(request, user);
        return SuccessResponse.ok("Order created successfully");
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<@NonNull SuccessResponse> cancelOrder(@PathVariable String orderId, @AuthenticationPrincipal User user) {
        orderService.cancelOrder(orderId, user);
        return SuccessResponse.ok("Order canceled successfully");
    }

    @GetMapping("/my")
    public ResponseEntity<@NonNull List<OrderResponse>> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getMyOrders(user));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<@NonNull SuccessResponse> editOrder(@PathVariable String orderId, @RequestParam Integer quantity, @AuthenticationPrincipal User user) {
        orderService.editQuantity(orderId, quantity, user);
        return SuccessResponse.ok("Quantity updated successfully");
    }

}