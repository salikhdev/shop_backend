package uz.salikhdev.shop_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
