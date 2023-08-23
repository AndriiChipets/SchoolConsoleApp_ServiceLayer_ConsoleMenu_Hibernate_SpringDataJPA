package ua.prom.roboticsdmc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.dto.GroupDto;

@Mapper(componentModel = "spring")
public interface GroupMapperStruct {

    GroupMapperStruct INSTANCE = Mappers.getMapper(GroupMapperStruct.class);

    GroupDto mapGroupToGroupDto(Group group);

    Group mapGroupDtoToGroup(GroupDto groupDto);

}
