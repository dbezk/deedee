package ua.project.deedee.entity.battle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.project.deedee.entity.user.DeeDeeUser;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "battles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private BattleInfo battleInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private BattlePlayersInfo battlePlayersInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private BattleShortInfo battleShortInfo;

    @OneToOne
    @JsonIgnore
    private DeeDeeUser battleCreator;

    @OneToOne
    @JsonIgnore
    private DeeDeeUser battleOpponent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BattleEvent> battleEventList;

    private LocalDateTime createdAt;

}
