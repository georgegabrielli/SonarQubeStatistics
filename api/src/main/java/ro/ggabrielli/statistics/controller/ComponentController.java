package ro.ggabrielli.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ggabrielli.statistics.converter.GroupConverter;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.dto.GroupDto;
import ro.ggabrielli.statistics.processor.SonarDataProcessor;
import ro.ggabrielli.statistics.repository.jpa.component.GroupRepository;

import java.util.Map;

@RestController
@RequestMapping(path = "/component")
public class ComponentController {

    private final GroupRepository repository;

    private final GroupConverter groupConverter;

    private final SonarDataProcessor sonarDataProcessor;

    public ComponentController (@Autowired GroupRepository repository,
                                @Autowired GroupConverter groupConverter,
                                @Autowired SonarDataProcessor sonarDataProcessor) {
        this.repository = repository;
        this.groupConverter = groupConverter;
        this.sonarDataProcessor = sonarDataProcessor;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addComponent (@RequestBody Map<String, GroupDto> groupDto) {

        Group group = groupConverter.convertToModel(groupDto.get("group"));
        repository.save(group);

        sonarDataProcessor.performDataUpdate();
    }

}
