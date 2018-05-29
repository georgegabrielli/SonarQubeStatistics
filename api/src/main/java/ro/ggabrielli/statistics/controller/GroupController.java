package ro.ggabrielli.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ggabrielli.statistics.converter.GroupConverter;
import ro.ggabrielli.statistics.converter.PathConverter;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.domain.component.Path;
import ro.ggabrielli.statistics.dto.GroupDto;
import ro.ggabrielli.statistics.processor.SonarDataProcessor;
import ro.ggabrielli.statistics.repository.jpa.component.GroupRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    private final GroupRepository repository;

    private final GroupConverter groupConverter;

    private final SonarDataProcessor sonarDataProcessor;

    private final PathConverter pathConverter;

    public GroupController (@Autowired GroupRepository repository,
                            @Autowired GroupConverter groupConverter,
                            @Autowired PathConverter pathConverter,
                            @Autowired SonarDataProcessor sonarDataProcessor) {
        this.repository = repository;
        this.groupConverter = groupConverter;
        this.sonarDataProcessor = sonarDataProcessor;
        this.pathConverter = pathConverter;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addGroup (@RequestBody Map<String, GroupDto> groupDto) {

        Group group = groupConverter.convertToModel(groupDto.get("group"));
        repository.save(group);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<GroupDto> getGroups (){
        return repository.findAll().stream().map(groupConverter::convertToDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{groupId}")
    public void deleteGroup(@PathVariable final Integer groupId){
        repository.delete(repository.findOne(groupId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "{groupId}")
    public GroupDto getGroupById(@PathVariable final Integer groupId){
        return groupConverter.convertToDto(repository.findOne(groupId));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{groupId}")
    public void updateGroup(@PathVariable final Integer groupId, @RequestBody Map<String, GroupDto> groupDtoMap){
        Group group = repository.findOne(groupId);

        GroupDto groupDto = groupDtoMap.get("group");

        List<Path> paths = pathConverter.convertListToModel(groupDto.getPaths());

        paths.stream().filter(path -> path.getId() == null).forEach(path -> group.getPaths().add(path));

        repository.save(group);

        sonarDataProcessor.performDataUpdate();
    }
}
