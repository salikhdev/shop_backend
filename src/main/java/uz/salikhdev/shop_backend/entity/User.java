package uz.salikhdev.shop_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "_users")
public class User extends BaseEntity implements UserDetails {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "address")
    private String address;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String token;

    @NotNull
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @NotNull
    @Override
    public String getUsername() {
        return email;
    }

    public enum Status {
        ACTIVE, UNVERIFIED, BLOCKED
    }

}
