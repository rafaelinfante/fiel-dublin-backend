package ie.fieldublin.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    Integer id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String lastname;

    String firstname;

    String password;

    String country;

    @NotNull(message = "Date created must not be empty")
    private LocalDateTime dateCreated;

    @NotNull(message = "Date edited must not be empty")
    private LocalDateTime dateEdited;
}
