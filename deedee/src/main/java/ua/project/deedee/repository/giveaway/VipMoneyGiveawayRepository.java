package ua.project.deedee.repository.giveaway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;

@Repository
public interface VipMoneyGiveawayRepository extends JpaRepository<VipMoneyGiveaway, Long> {
}
