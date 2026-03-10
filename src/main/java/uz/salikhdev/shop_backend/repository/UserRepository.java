package uz.salikhdev.shop_backend.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull String> {

    Optional<@NonNull User> findByEmail(@NonNull String s);

    Optional<User> findByToken(String token);
}
