package uz.salikhdev.shop_backend.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.Color;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<@NonNull Color, @NonNull String> {

    List<Color> findAllByDeletedAtIsNull();

    Optional<Color> findByIdAndDeletedAtIsNull(String id);

    boolean existsByHexAndDeletedAtIsNull(String hex);

    boolean existsByHex(String hex);

    Optional<Color> findByHex(String hex);

}
