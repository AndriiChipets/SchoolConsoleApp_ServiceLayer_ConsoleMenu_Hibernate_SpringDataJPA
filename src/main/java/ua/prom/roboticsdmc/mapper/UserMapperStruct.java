package ua.prom.roboticsdmc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapperStruct {

    UserMapperStruct INSTANCE = Mappers.getMapper(UserMapperStruct.class);

    @Mapping(target = "password", ignore = true)
    UserDto maUserToUserDto(User user);

    User mapUserDtoToUser(UserDto userDto);

}
