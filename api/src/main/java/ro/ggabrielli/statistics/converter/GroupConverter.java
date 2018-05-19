package ro.ggabrielli.statistics.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.dto.GroupDto;


@Component
public class GroupConverter {

    private final PathConverter pathConverter;

    public GroupConverter (@Autowired PathConverter pathConverter) {
        this.pathConverter = pathConverter;
    }

    public GroupDto convertToDto (Group group) {
        return GroupDto.builder().name(group.getName()).paths(pathConverter.convertListToDto(group.getPaths())).build();
    }

    public Group convertToModel (GroupDto groupDto) {
        return Group.builder().name(groupDto.getName()).paths(pathConverter.convertListToModel(groupDto.getPaths())).build();
    }
}
