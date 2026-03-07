package uz.salikhdev.shop_backend.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<@NonNull Category, @NonNull String> {

    List<Category> findAllByDeletedAtIsNull();

    Optional<Category> findByIdAndDeletedAtIsNull(String id);

    Optional<Category> findByName(String name);

    boolean existsByNameAndDeletedAtIsNull(String name);

    boolean existsByName(String name);
}
