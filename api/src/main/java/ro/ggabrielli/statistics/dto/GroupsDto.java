package ro.ggabrielli.statistics.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupsDto implements Serializable {
    private List<GroupDto> groups;
}
