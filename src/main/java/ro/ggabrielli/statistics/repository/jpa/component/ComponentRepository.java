package ro.ggabrielli.statistics.repository.jpa.componnent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ggabrielli.statistics.domain.component.Group;

@Repository
public interface ComponentRepository extends JpaRepository<Group, Integer> {
}
