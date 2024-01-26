package ie.fieldublin.domain.user.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    Integer id;
    String username;
    String firstname;
    String lastname;
    String country;
}
