package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.data.DoneBattleData;
import ua.project.deedee.dto.battle.*;
import ua.project.deedee.entity.battle.*;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.enums.BalanceUpdateType;
import ua.project.deedee.enums.BattleEventType;
import ua.project.deedee.enums.BattleWinner;
import ua.project.deedee.exception.EntityNotFoundException;
import ua.project.deedee.exception.IllegalTypeException;
import ua.project.deedee.exception.NotEnoughBalanceException;
import ua.project.deedee.repository.battle.BattleRepository;
import ua.project.deedee.service.IBattleService;
import ua.project.deedee.service.IEntityConverterService;
import ua.project.deedee.service.IUserBalanceService;
import ua.project.deedee.service.IUserService;
import ua.project.deedee.ws.service.IWSService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BattleService implements IBattleService {

    int MIN_MONEY_BET = 100;
    int MIN_VIP_MONEY_BET = 2;

    int MAX_MONEY_BET = 50_000;
    int MAX_VIP_MONEY_BET = 100;

    int EVENT_SECONDS_DELAY = 2;
    int STANDARD_SECONDS_DELAY = 3;

    IUserService userService;
    IUserBalanceService userBalanceService;
    IWSService iwsService;
    IEntityConverterService entityConverterService;

    BattleRepository battleRepository;

    List<DoneBattleData> finishedBattlesList;

    // TODO: Refactor code

    Random random;

    @Override
    public Battle getBattleById(Long id) {
        return battleRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(
                    String.format("Battle with id %d not found.", id)
            );
        });
    }

    @Override
    public ActiveBattleInfoDto getActiveBattleInfo(Long id) {
        var battle = getBattleById(id);
        if(battle.getBattleOpponent() == null) {
            return entityConverterService.convertBattleToActiveFullInfoBattleDto(battle);
        } else {
            throw new IllegalTypeException("Game already started.");
        }
    }

    @Override
    public void joinBattle(Long id, Long botId) {
        var opponent = userService.getUserById(botId != null ? botId : userService.getUserId());
        var battle = getBattleById(id);
        if(userBalanceService.checkUserBalance(opponent, battle.getBattleInfo().getMoney(),
                battle.getBattleInfo().getVipMoney())) {
            if(battle.getBattleCreator() == opponent) {
                throw new IllegalTypeException("You can't join your own battle.");
            }
            if(battle.getBattleOpponent() == null) {
                userBalanceService.updateBalance(opponent, battle.getBattleInfo().getMoney(),
                        battle.getBattleInfo().getVipMoney(), BalanceUpdateType.CHARGE);
                battle.setBattleOpponent(opponent);
                battleRepository.save(generateBattle(battle));
                iwsService.sendBattleData(FullBattleDto.builder()
                        .battleId(battle.getId())
                        .battleWinner(battle.getBattleInfo().getWinner())
                        .winMoney(battle.getBattleInfo().getMoney())
                        .winVipMoney(battle.getBattleInfo().getVipMoney())
                        .battleEventList(battle.getBattleEventList())
                        .creator(FullBattleUserDto.builder()
                                .avatar(battle.getBattleCreator().getAvatar())
                                .firstName(battle.getBattleCreator().getFirstName())
                                .lastName(battle.getBattleCreator().getLastName())
                                .startHealth(battle.getBattlePlayersInfo().getCreatorStartHealth())
                                .id(battle.getBattleCreator().getId()).build())
                        .opponent(FullBattleUserDto.builder()
                                .avatar(battle.getBattleOpponent().getAvatar())
                                .firstName(battle.getBattleOpponent().getFirstName())
                                .lastName(battle.getBattleOpponent().getLastName())
                                .startHealth(battle.getBattlePlayersInfo().getOpponentStartHealth())
                                .id(battle.getBattleOpponent().getId()).build())
                        .build());
                var battleResult = DoneBattleData.builder()
                        .battleId(battle.getId())
                        .endsAt(
                                LocalDateTime.now().plusSeconds((long) battle.getBattleEventList().size() * EVENT_SECONDS_DELAY+STANDARD_SECONDS_DELAY)
                        ).build();
                finishedBattlesList.add(battleResult);
            } else {
                throw new IllegalTypeException(
                        String.format("Battle with id %d already started." , id)
                );
            }
        } else {
            throw new NotEnoughBalanceException("You don't have enough money.");
        }
    }

    @Override
    public Battle generateBattle(Battle battle) {
        var creator = battle.getBattleCreator();
        var opponent = battle.getBattleOpponent();

        var creatorHealth = creator.getCharacterStatistic().getHealth();
        var opponentHealth = opponent.getCharacterStatistic().getHealth();

        battle.getBattlePlayersInfo().setCreatorStartHealth(creatorHealth);
        battle.getBattlePlayersInfo().setOpponentStartHealth(opponentHealth);

        boolean creatorStep = true;
        while(creatorHealth > 0 && opponentHealth > 0) {
            var battleEvent = BattleEvent.builder()
                    .isCreator(creatorStep)
                    .build();
            var uniqueAttack = creatorStep ?
                    creator.getCharacterStatistic().getUniqueProduct() : opponent.getCharacterStatistic().getUniqueProduct();
            var event = getRandomBattleEvent(creatorStep ? creatorHealth : opponentHealth, uniqueAttack);
            var attackDamage = 0;

            if(event == BattleEventType.ATTACK) {
                var characterStatistic = creatorStep ? creator.getCharacterStatistic() : opponent.getCharacterStatistic();
                attackDamage = Math.max(random.nextInt(30)+1, (characterStatistic.getPower()*30/100));
            } else if(event == BattleEventType.UNIQUE_ATTACK && uniqueAttack != null) {
                attackDamage = Math.max(random.nextInt(30)+1, (uniqueAttack.getProductInfo().getPower()*30/100));
            } else if(event == BattleEventType.HEAL) {
                var healCount = creatorStep ? creatorHealth * 2 : opponentHealth * 2;
                battleEvent.setHealCount(healCount);
                if(creatorStep) {
                    creatorHealth += healCount;
                } else {
                    opponentHealth += healCount;
                }
            }
            if(attackDamage > 0) {
                battleEvent.setDamageCount(attackDamage);
                if(creatorStep) {
                    opponentHealth -= attackDamage;
                } else {
                    creatorHealth -= attackDamage;
                }
            }
            battleEvent.setEventType(event);
            battleEvent.setCreatorHealth(creatorHealth);
            battleEvent.setOpponentHealth(opponentHealth);
            battle.getBattleEventList().add(battleEvent);
            creatorStep = !creatorStep;
        }
        var winnerSide = 0 >= creatorHealth ? BattleWinner.OPPONENT : BattleWinner.CREATOR;
        battle.getBattleInfo().setWinner(winnerSide);
        return battle;
    }

    @Override
    public BattleEventType getRandomBattleEvent(int health, UniqueProduct uniqueProduct) {
        if(health > 20 && uniqueProduct != null) {
            return (random.nextInt(2) == 1) ? BattleEventType.UNIQUE_ATTACK : BattleEventType.ATTACK;
        } else if(health < 20) {
            return (random.nextInt(2) == 1) ? BattleEventType.HEAL : BattleEventType.ATTACK;
        } else {
            return BattleEventType.ATTACK;
        }
    }

    @Override
    public void createNewBattle(CreateBattleDto battleDto, Long botId) {
        // TODO: START HEALTH
        if(isValidBet(battleDto.getMoney(), battleDto. getVipMoney())) {
            var user = userService.getUserById(botId == null ? userService.getUserId() : botId);
            if (userBalanceService.checkUserBalance(user, battleDto.getMoney(), battleDto.getVipMoney())) {
                var battle = Battle.builder()
                        .battleCreator(user)
                        .battleOpponent(null)
                        .battlePlayersInfo(new BattlePlayersInfo())
                        .battleInfo(
                                BattleInfo.builder()
                                        .money(battleDto.getMoney())
                                        .vipMoney(battleDto.getVipMoney())
                                        .winner(BattleWinner.NONE)
                                        .build()
                        )
                        .battleEventList(new ArrayList<>())
                        .battleShortInfo(
                                BattleShortInfo.builder()
                                        .creatorId(user.getId())
                                        .creatorFirstName(user.getFirstName())
                                        .creatorLastName(user.getLastName())
                                        .creatorAvatar(user.getAvatar())
                                        .creatorRating(user.getUserStatistic().getRating())
                                        .build()
                        )
                        .createdAt(LocalDateTime.now()).build();
                battleRepository.save(battle);
                userBalanceService.updateBalance(user, battleDto.getMoney(), battleDto.getVipMoney(), BalanceUpdateType.CHARGE);
            } else {
                throw new NotEnoughBalanceException("You don't have enough money.");
            }
        } else {
            throw new IllegalTypeException(
                    String.format("Money bet range is [%d;%d], vip money bet range is [%d;%d].",
                            MIN_MONEY_BET, MAX_MONEY_BET, MIN_VIP_MONEY_BET, MAX_VIP_MONEY_BET)
            );
        }
    }



    @Override
    public List<ActiveBattleDto> getAllActiveBattles() {
        return battleRepository.findAllActiveBattles().stream()
                .map(entityConverterService::convertBattleToActiveBattleDto).toList();
    }

    @Override
    public boolean isValidBet(Integer money, Integer vipMoney) {
        if(money != null) {
            return money >= MIN_MONEY_BET && money <= MAX_MONEY_BET;
        }
        if(vipMoney != null) {
            return vipMoney >= MIN_VIP_MONEY_BET && vipMoney <= MAX_VIP_MONEY_BET;
        }
        return false;
    }

}
