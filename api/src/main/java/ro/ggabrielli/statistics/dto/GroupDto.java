package ro.ggabrielli.statistics.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto implements Serializable{

    private String name;

    private PathsDto paths;
}
