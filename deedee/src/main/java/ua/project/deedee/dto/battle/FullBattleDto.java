package ua.project.deedee.dto.battle;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.project.deedee.dto.user.OnlineUserDto;
import ua.project.deedee.entity.battle.BattleEvent;
import ua.project.deedee.enums.BattleWinner;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FullBattleDto {

    private Long battleId;

    @Nullable
    private List<BattleEvent> battleEventList;

    private FullBattleUserDto creator;

    private FullBattleUserDto opponent;

    private BattleWinner battleWinner;

    private Integer winMoney;

    private Integer winVipMoney;

}
