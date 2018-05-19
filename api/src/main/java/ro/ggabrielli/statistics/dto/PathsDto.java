package ro.ggabrielli.statistics.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PathsDto implements Serializable{

    private List<PathDto> paths;
}
