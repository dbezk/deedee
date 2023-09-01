package ua.project.deedee.repository.giveaway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;

@Repository
public interface MoneyGiveawayRepository extends JpaRepository<MoneyGiveaway, Long> {
}
