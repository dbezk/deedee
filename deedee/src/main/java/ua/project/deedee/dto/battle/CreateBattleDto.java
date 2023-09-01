package ua.project.deedee.dto.battle;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateBattleDto {

    private Integer money;

    private Integer vipMoney;

}
