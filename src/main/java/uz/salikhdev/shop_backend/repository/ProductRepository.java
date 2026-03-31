package uz.salikhdev.shop_backend.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.salikhdev.shop_backend.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<@NonNull Product, @NonNull String>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByDeletedAtIsNull();

    Optional<Product> findByIdAndDeletedAtIsNull(String id);

    List<Product> findAllByCategoryIdAndDeletedAtIsNull(String categoryId);

    boolean existsByTitleAndDeletedAtIsNull(String title);

    Optional<Product> findByTitle(String title);
}
