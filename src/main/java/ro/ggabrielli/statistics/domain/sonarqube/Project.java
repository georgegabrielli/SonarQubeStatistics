package ro.ggabrielli.statistics.domain.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by gobi on 5/9/2018.
 */

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
}
