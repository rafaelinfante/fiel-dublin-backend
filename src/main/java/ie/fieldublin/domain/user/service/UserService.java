package ie.fieldublin.domain.user.service;

import ie.fieldublin.domain.user.dto.UserRequestDto;
import ie.fieldublin.domain.user.repository.UserRepository;
import ie.fieldublin.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserRequestDto getUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return UserRequestDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .country(user.getCountry())
                    .build();
        }
        return null;
    }

    public List<UserRequestDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserRequestDto> usersDto = new ArrayList<>();
        if (!users.isEmpty()) {
            users.forEach(user -> {
                usersDto.add(UserRequestDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .country(user.getCountry())
                        .build());
            });
        }
        return usersDto;
    }
}
