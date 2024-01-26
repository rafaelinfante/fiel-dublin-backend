package ie.fieldublin.domain.user.service.mapper;


import ie.fieldublin.domain.user.dto.UserRequestDto;
import ie.fieldublin.domain.user.dto.UserResponseDto;
import ie.fieldublin.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto userDto);

    List<UserResponseDto> toDtoList(List<User> users);
}
