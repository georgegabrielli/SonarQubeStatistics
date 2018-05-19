package ro.ggabrielli.statistics.converter;

import org.springframework.stereotype.Component;
import ro.ggabrielli.statistics.domain.component.Path;
import ro.ggabrielli.statistics.dto.PathDto;
import ro.ggabrielli.statistics.dto.PathsDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PathConverter {

    public PathsDto convertListToDto(List<Path> paths) {
        return PathsDto.builder().paths(paths.stream().map(this::convertToDto).collect(Collectors.toList())).build();

    }

    public PathDto convertToDto(Path path) {
        return PathDto.builder().value(path.getValue()).build();
    }

    public List<Path> convertListToModel(PathsDto paths) {
        return paths.getPaths().stream().map(pathDto -> Path.builder().value(pathDto.getValue()).build()).collect(Collectors.toList());
    }
}
