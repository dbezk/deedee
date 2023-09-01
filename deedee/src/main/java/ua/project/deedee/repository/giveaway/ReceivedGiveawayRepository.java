package ua.project.deedee.repository.giveaway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.giveaway.ReceivedGiveaway;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.enums.GiveawayType;

@Repository
public interface ReceivedGiveawayRepository extends JpaRepository<ReceivedGiveaway, Long> {

    ReceivedGiveaway findFirstByUserAndAndGiveawayTypeOrderByTimestampDesc(DeeDeeUser user, GiveawayType giveawayType);

}
