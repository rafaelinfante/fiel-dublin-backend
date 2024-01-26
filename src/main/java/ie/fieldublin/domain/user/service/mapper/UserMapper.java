package ie.fieldublin.domain.user.service.mapper;


import ie.fieldublin.common.mapper.EntityMapper;
import ie.fieldublin.domain.user.dto.UserResponseDto;
import ie.fieldublin.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper extends EntityMapper<UserResponseDto, User> {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
}
