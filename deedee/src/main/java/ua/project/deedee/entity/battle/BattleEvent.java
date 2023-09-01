package ua.project.deedee.entity.battle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.project.deedee.enums.BattleEventType;

@Entity
@Table(name = "battles_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int healCount;

    private int damageCount;

    private int creatorHealth;

    private int opponentHealth;

    private BattleEventType eventType;

    private boolean isCreator;

}
