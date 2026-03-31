package uz.salikhdev.shop_backend.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import uz.salikhdev.shop_backend.entity.Product;

import java.math.BigDecimal;
import java.util.Locale;

public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<Product> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

    public static Specification<Product> titleContains(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        String pattern = "%" + title.toLowerCase(Locale.ROOT) + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), pattern);
    }

    public static Specification<Product> categoryId(String categoryId) {
        if (categoryId == null || categoryId.isBlank()) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> minPrice(BigDecimal minPrice) {
        if (minPrice == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> inStock(Boolean inStock) {
        if (inStock == null) {
            return null;
        }
        if (Boolean.TRUE.equals(inStock)) {
            return (root, query, cb) -> cb.greaterThan(root.get("quantity"), 0);
        }
        return (root, query, cb) -> cb.equal(root.get("quantity"), 0);
    }
}
