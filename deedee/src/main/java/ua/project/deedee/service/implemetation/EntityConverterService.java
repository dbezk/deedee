package ua.project.deedee.service.implemetation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.dto.battle.*;
import ua.project.deedee.dto.character.CharacterStatisticDto;
import ua.project.deedee.dto.character.DeeDeeCharacterDto;
import ua.project.deedee.dto.market.ProductDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.dto.user.UserStatisticDto;
import ua.project.deedee.entity.battle.Battle;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.service.IEntityConverterService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EntityConverterService implements IEntityConverterService {

    ObjectMapper objectMapper;

    @Override
    public DeeDeeUserPersonalInfoDto convertUserPersonalInfoToDto(DeeDeeUser user) {
        DeeDeeUserPersonalInfoDto dto = new DeeDeeUserPersonalInfoDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAvatar(user.getAvatar());
        dto.setDeeDeeCharacter(objectMapper.convertValue(user.getDeeDeeCharacter(), DeeDeeCharacterDto.class));
        dto.setCharacterStatistic(objectMapper.convertValue(user.getCharacterStatistic(), CharacterStatisticDto.class));
        dto.setUserStatistic(objectMapper.convertValue(user.getUserStatistic(), UserStatisticDto.class));
        if(user.getUniqueProducts() != null) {
            List<ProductDto> uniqueProducts = user.getUniqueProducts().stream()
                    .map(p -> objectMapper.convertValue(p, ProductDto.class)).toList();
            dto.setUniqueProducts(uniqueProducts);
        } else {
            dto.setUniqueProducts(new ArrayList<>());
        }
        dto.setMoney(user.getMoney());
        dto.setVipMoney(user.getVipMoney());
        return dto;
    }

    @Override
    public FullBattleDto convertBattleToFullBattleDto(Battle battle) {
        return FullBattleDto.builder()
                .battleId(battle.getId())
                .battleEventList(battle.getBattleEventList())
                .creator(FullBattleUserDto.builder()
                        .id(battle.getBattleCreator().getId())
                        .firstName(battle.getBattleCreator().getFirstName())
                        .lastName(battle.getBattleCreator().getLastName())
                        .avatar(battle.getBattleCreator().getAvatar())
                        .startHealth(battle.getBattleOpponent() == null ?
                                battle.getBattleCreator().getCharacterStatistic().getHealth() : battle.getBattlePlayersInfo().getCreatorStartHealth()).build())
                .opponent(battle.getBattleOpponent() == null ? null : FullBattleUserDto.builder()
                        .id(battle.getBattleOpponent().getId())
                        .firstName(battle.getBattleOpponent().getFirstName())
                        .lastName(battle.getBattleOpponent().getLastName())
                        .avatar(battle.getBattleOpponent().getAvatar())
                        .startHealth(battle.getBattlePlayersInfo().getOpponentStartHealth()).build())
                .battleWinner(battle.getBattleInfo().getWinner())
                .winMoney(battle.getBattleInfo().getMoney())
                .winVipMoney(battle.getBattleInfo().getVipMoney()).build();
    }

    @Override
    public ActiveBattleDto convertBattleToActiveBattleDto(Battle battle) {
        return ActiveBattleDto.builder()
                .battleId(battle.getId())
                .battleCreator(objectMapper.convertValue(battle.getBattleCreator(), FullBattleUserDto.class))
                .money(battle.getBattleInfo().getMoney())
                .vipMoney(battle.getBattleInfo().getVipMoney()).build();
    }

    @Override
    public ActiveBattleInfoDto convertBattleToActiveFullInfoBattleDto(Battle battle) {
        return ActiveBattleInfoDto.builder()
                .creatorInfo(ActiveBattleUserDto.builder()
                        .avatar(battle.getBattleCreator().getAvatar())
                        .firstName(battle.getBattleCreator().getFirstName())
                        .lastName(battle.getBattleCreator().getLastName())
                        .rating(battle.getBattleCreator().getUserStatistic().getRating())
                        .userStatistic(objectMapper.convertValue(battle.getBattleCreator().getCharacterStatistic(), CharacterStatisticDto.class)).build())
                .money(battle.getBattleInfo().getMoney())
                .vipMoney(battle.getBattleInfo().getVipMoney()).build();
    }

}
