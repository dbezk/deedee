package ua.project.deedee.entity.battle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.project.deedee.enums.BattleWinner;

@Entity
@Table(name = "battles_infos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer money;

    private Integer vipMoney;

    private int creatorStartHealth;

    @Enumerated(value = EnumType.STRING)
    private BattleWinner winner;

}
