package ro.ggabrielli.statistics.converter;

import org.springframework.stereotype.Component;
import ro.ggabrielli.statistics.domain.component.Path;
import ro.ggabrielli.statistics.dto.PathDto;
import ro.ggabrielli.statistics.dto.PathsDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PathConverter {

    public PathsDto convertListToDto(List<Path> paths) {
        return PathsDto.builder().paths(paths.stream().map(this::convertToDto).collect(Collectors.toList())).build();

    }

    public PathDto convertToDto(Path path) {
        return PathDto.builder().id(path.getId()).value(path.getValue()).build();
    }

    public List<Path> convertListToModel(List<PathDto> pathsDto) {
        return pathsDto == null ? new ArrayList<>() : pathsDto.stream().map(pathDto -> Path.builder().id(pathDto.getId()).value(pathDto.getValue()).build()).collect(Collectors.toList());
    }
}
