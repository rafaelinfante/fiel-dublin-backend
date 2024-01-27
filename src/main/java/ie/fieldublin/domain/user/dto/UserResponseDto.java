package ie.fieldublin.domain.user.dto;

import ie.fieldublin.domain.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    Set<RoleDto> roles;
}
