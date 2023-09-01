package ua.project.deedee.entity.battle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "battles_short_infos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleShortInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long creatorId;

    private int creatorRating;

    private String creatorFirstName;

    private String creatorLastName;

    private String creatorAvatar;

}
