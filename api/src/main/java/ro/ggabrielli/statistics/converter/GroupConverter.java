package ro.ggabrielli.statistics.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.dto.GroupDto;

import java.util.stream.Collectors;


@Component
public class GroupConverter {

    private final PathConverter pathConverter;

    public GroupConverter (@Autowired PathConverter pathConverter) {
        this.pathConverter = pathConverter;
    }

    public GroupDto convertToDto (Group group) {
        return GroupDto.builder()
                       .id(group.getId())
                       .name(group.getName())
                       .paths(group.getPaths()
                                   .stream()
                                   .map(pathConverter::convertToDto)
                                   .collect(Collectors.toList()))
                       .build();
    }

    public Group convertToModel (GroupDto groupDto) {
        return Group.builder().id(groupDto.getId()).name(groupDto.getName()).paths(pathConverter.convertListToModel(groupDto.getPaths())).build();
    }
}
