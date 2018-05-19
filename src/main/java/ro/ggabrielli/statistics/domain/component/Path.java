package ro.ggabrielli.statistics.domain.component;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "path")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Path implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "path")
    private String value;

    @ManyToMany(mappedBy = "paths")
    private List<Group> groups;
}
