package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.project.deedee.dto.giveaway.GiveawayDto;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;
import ua.project.deedee.enums.GiveawayType;
import ua.project.deedee.service.IGiveawayService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/giveaway")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiveawayApi {

    IGiveawayService giveawayService;

    @GetMapping(value = "/money")
    public ResponseEntity<List<GiveawayDto>> getAllMoneyGiveaways() {
        return ResponseEntity.ok(giveawayService.getAllMoneyGiveaways());
    }

    @PostMapping(value = "/money/{id}")
    public ResponseEntity<?> collectMoneyGiveaway(
            @PathVariable(value = "id") Long id
    ) {
        giveawayService.collectGiveaway(id, GiveawayType.MONEY_GIVEAWAY);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/vipMoney")
    public ResponseEntity<List<GiveawayDto>> getAllVipMoneyGiveaways() {
        return ResponseEntity.ok(giveawayService.getAllVipMoneyGiveaways());
    }

    @PostMapping(value = "/vipMoney/{id}")
    public ResponseEntity<?> collectVipMoneyGiveaway(
            @PathVariable(value = "id") Long id
    ) {
        giveawayService.collectGiveaway(id, GiveawayType.VIP_MONEY_GIVEAWAY);
        return ResponseEntity.ok().build();
    }

}
