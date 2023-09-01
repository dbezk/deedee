package ua.project.deedee.dto.battle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActiveBattleDto {

    Long battleId;

    FullBattleUserDto battleCreator;

    Integer money;

    Integer vipMoney;

}
