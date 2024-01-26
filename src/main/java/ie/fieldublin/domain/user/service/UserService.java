package ie.fieldublin.domain.user.service;

import ie.fieldublin.domain.user.dto.UserResponseDto;
import ie.fieldublin.domain.user.entity.User;
import ie.fieldublin.domain.user.repository.UserRepository;
import ie.fieldublin.domain.user.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        UserResponseDto userResponseDto = UserMapper.MAPPER.toDto(user);

        /*if (user != null) {
            return UserRequestDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .country(user.getCountry())
                    .build();
        }*/

        return userResponseDto;
    }

    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> usersDto = new ArrayList<>();

        usersDto = UserMapper.MAPPER.toDtoList(users);

        /*if (!users.isEmpty()) {
            users.forEach(user -> {
                usersDto.add(UserRequestDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .country(user.getCountry())
                        .build());
            });
        }*/

        return usersDto;
    }
}
