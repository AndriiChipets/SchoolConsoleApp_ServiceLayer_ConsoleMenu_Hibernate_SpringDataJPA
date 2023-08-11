package ua.prom.roboticsdmc.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Group {

    private final int groupId;
    private final String groupName;

    public static class GroupBuilder {

        public GroupBuilder withGroupName(String groupName) {
            this.groupName = groupName != null ? groupName.trim() : null;
            return this;
        }
    }
}
