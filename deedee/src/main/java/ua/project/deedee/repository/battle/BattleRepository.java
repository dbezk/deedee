package ua.project.deedee.repository.battle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.battle.Battle;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    @Query(value = "SELECT b FROM Battle b WHERE b.battleOpponent = null ORDER BY b.id DESC")
    List<Battle> findAllActiveBattles();

}
