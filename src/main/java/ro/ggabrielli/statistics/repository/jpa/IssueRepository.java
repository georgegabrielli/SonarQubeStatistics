package ro.ggabrielli.statistics.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ggabrielli.statistics.domain.sonarqube.Issue;

/**
 * Created by gobi on 5/9/2018.
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
}
