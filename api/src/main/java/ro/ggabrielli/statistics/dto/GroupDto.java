package ro.ggabrielli.statistics.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto implements Serializable{

    private Integer id;

    private String name;

    private List<PathDto> paths;
}
