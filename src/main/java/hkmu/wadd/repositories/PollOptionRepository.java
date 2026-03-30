package hkmu.wadd.repositories;

import hkmu.wadd.models.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollOptionRepository extends JpaRepository<PollOption, Long> {
}