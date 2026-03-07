package uz.salikhdev.shop_backend.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.Size;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<@NonNull Size, @NonNull String> {

    List<Size> findAllByDeletedAtIsNull();

    Optional<Size> findByIdAndDeletedAtIsNull(String id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);

    Optional<Size> findByName(String name);
}
