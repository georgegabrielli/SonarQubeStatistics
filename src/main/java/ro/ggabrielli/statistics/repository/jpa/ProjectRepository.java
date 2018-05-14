package ro.ggabrielli.statistics.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ggabrielli.statistics.domain.sonarqube.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
