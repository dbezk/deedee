package ua.project.deedee.service;

import ua.project.deedee.dto.battle.ActiveBattleDto;
import ua.project.deedee.dto.battle.ActiveBattleInfoDto;
import ua.project.deedee.dto.battle.FullBattleDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.entity.battle.Battle;
import ua.project.deedee.entity.user.DeeDeeUser;

public interface IEntityConverterService {

    public DeeDeeUserPersonalInfoDto convertUserPersonalInfoToDto(DeeDeeUser user);

    public FullBattleDto convertBattleToFullBattleDto(Battle battle);

    public ActiveBattleDto convertBattleToActiveBattleDto(Battle battle);

    public ActiveBattleInfoDto convertBattleToActiveFullInfoBattleDto(Battle battle);

}
