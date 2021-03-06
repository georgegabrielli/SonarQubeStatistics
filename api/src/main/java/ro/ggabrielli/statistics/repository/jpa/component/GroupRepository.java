package ro.ggabrielli.statistics.repository.jpa.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ggabrielli.statistics.domain.component.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
