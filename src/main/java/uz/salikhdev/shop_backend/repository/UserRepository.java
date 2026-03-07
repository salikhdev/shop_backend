package uz.salikhdev.shop_backend.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull String> {
}
