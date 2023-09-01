package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.project.deedee.dto.battle.ActiveBattleInfoDto;
import ua.project.deedee.dto.battle.CreateBattleDto;
import ua.project.deedee.service.IBattleService;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/battle")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BattleApi {

    IBattleService battleService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ActiveBattleInfoDto> getFullBattleInfo(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseEntity.ok(battleService.getActiveBattleInfo(id));
    }

    @PostMapping(value = "/newBattle")
    public ResponseEntity<?> createNewBattle(
            @RequestBody CreateBattleDto battleDto
            ) {
        battleService.createNewBattle(battleDto, null);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/battle/newBattle").toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/join/{id}")
    public ResponseEntity<?> joinToBattle(
            @PathVariable(value = "id") Long id
    ) {
        battleService.joinBattle(id, null);
        return ResponseEntity.ok().build();
    }

}
