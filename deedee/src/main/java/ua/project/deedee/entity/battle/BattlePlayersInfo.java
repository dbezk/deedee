package ua.project.deedee.entity.battle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "battles_players_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattlePlayersInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int creatorStartHealth;

    private int opponentStartHealth;

}
