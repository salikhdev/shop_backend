package uz.salikhdev.shop_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "id")
    private Product product;

    private Integer quantity;

    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        ACTIVE,
        DONE,
        CANCELED
    }

}
