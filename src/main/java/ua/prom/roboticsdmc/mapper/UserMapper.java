package ua.prom.roboticsdmc.mapper;

import ua.prom.roboticsdmc.domain.User;
import ua.prom.roboticsdmc.dto.UserDto;

public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public User mapDomainToEntity(UserDto userDto) {
        return userDto == null ? null :
            User.builder()
                   .withUserId(userDto.getUserId())
                   .withFirstName(userDto.getFirstName())
                   .withLastName(userDto.getLastName())
                   .withEmail(userDto.getEmail())
                   .withPassword(userDto.getPassword())
                   .build();
    }

    @Override
    public UserDto mapEntityToDomain(User user) {
        return user == null ? null :
            UserDto.builder()
                   .withUserId(user.getUserId())
                   .withFirstName(user.getFirstName())
                   .withLastName(user.getLastName())
                   .withEmail(user.getEmail())
                   .withPassword(user.getPassword())
                   .build();
    }
}
