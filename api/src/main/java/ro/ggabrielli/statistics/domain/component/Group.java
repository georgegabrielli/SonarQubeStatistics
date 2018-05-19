package ro.ggabrielli.statistics.domain.component;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "component")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "group_path",
    joinColumns = @JoinColumn(name = "group_id"),
    inverseJoinColumns = @JoinColumn(name = "path_id"))
    private List<Path> paths;
}
