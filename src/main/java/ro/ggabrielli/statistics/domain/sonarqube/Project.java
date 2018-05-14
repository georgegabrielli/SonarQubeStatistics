package ro.ggabrielli.statistics.domain.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "scope")
    private String scope;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "project_uuid")
    private String projectUuid;

    @Column(name = "uuid_path")
    private String pathUuid;
}
