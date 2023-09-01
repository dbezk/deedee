package ua.project.deedee.ws.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import ua.project.deedee.dto.battle.FullBattleDto;
import ua.project.deedee.enums.BattleWinner;
import ua.project.deedee.service.IBattleService;
import ua.project.deedee.service.IEntityConverterService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WSBattleController {

    IBattleService battleService;
    IEntityConverterService converterService;

    @SubscribeMapping(value = "/battle/{battleId}")
    public FullBattleDto getBattle(
            @DestinationVariable(value = "battleId") Long battleId) {
        var battle = battleService.getBattleById(battleId);
        if(battle.getBattleInfo().getWinner() != BattleWinner.NONE
                && battle.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            return null;
        }
        return converterService.convertBattleToFullBattleDto(battle);
    }

}
