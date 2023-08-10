package ua.prom.roboticsdmc.mapper;

import org.springframework.stereotype.Service;

import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.dto.GroupDto;

@Service
public class GroupMapper implements Mapper<Group, GroupDto> {

    @Override
    public Group mapDomainToEntity(GroupDto groupDto) {
        return groupDto == null ? null :
            Group.builder()
                    .withGroupId(groupDto.getGroupId())
                    .withGroupName(groupDto.getGroupName())
                    .build();
    }

    @Override
    public GroupDto mapEntityToDomain(Group group) {
        return group == null ? null :
            GroupDto.builder()
                    .withGroupId(group.getGroupId())
                    .withGroupName(group.getGroupName())
                    .build();
    }
}
