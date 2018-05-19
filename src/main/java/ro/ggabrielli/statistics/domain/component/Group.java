package ro.ggabrielli.statistics.domain.component;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "component")
@Table(schema = "component")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Component implements Serializable{
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;
}
