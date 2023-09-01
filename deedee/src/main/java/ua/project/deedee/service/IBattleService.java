package ua.project.deedee.service;

import ua.project.deedee.dto.battle.ActiveBattleDto;
import ua.project.deedee.dto.battle.ActiveBattleInfoDto;
import ua.project.deedee.dto.battle.CreateBattleDto;
import ua.project.deedee.entity.battle.Battle;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.enums.BattleEventType;

import java.util.List;

public interface IBattleService {

    public Battle getBattleById(Long id);

    public ActiveBattleInfoDto getActiveBattleInfo(Long id);

    public void joinBattle(Long id, Long botId);

    public Battle generateBattle(Battle battle);

    public BattleEventType getRandomBattleEvent(int health, UniqueProduct uniqueProduct);

    public void createNewBattle(CreateBattleDto battleDto, Long botId);

    public List<ActiveBattleDto> getAllActiveBattles();

    public boolean isValidBet(Integer money, Integer vipMoney);

}
