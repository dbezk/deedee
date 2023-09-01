package ua.project.deedee.dto.battle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActiveBattleInfoDto {

    private Long id;

    private ActiveBattleUserDto creatorInfo;

    private Integer money;

    private Integer vipMoney;

}
