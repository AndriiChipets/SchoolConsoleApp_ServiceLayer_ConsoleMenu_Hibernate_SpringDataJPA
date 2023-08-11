package ua.prom.roboticsdmc.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupDto {

    private final int groupId;
    @ToString.Include(name = "\t" + "GroupName")
    private final String groupName;

    public static class GroupDtoBuilder {

        public GroupDtoBuilder withGroupName(String groupName) {
            this.groupName = groupName != null ? groupName.trim() : null;
            return this;
        }
    }
}
