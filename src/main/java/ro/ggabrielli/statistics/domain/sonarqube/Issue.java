package ro.ggabrielli.statistics.domain.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by gobi on 5/8/2018.
 */

@Entity(name = "issues")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Issue implements Serializable{

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "component_uuid")
    private String componentUUID;

    @Column(name = "project_uuid")
    private String projectUUID;

    @Column(name = "severity")
    private String severity;

    @Column(name = "issue_type")
    private int issueType;
}
