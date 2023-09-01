package ua.project.deedee.service;

import ua.project.deedee.dto.giveaway.GiveawayDto;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;
import ua.project.deedee.enums.GiveawayType;

import java.util.List;

public interface IGiveawayService {

    void collectGiveaway(Long id, GiveawayType giveawayType);

    List<GiveawayDto> getAllMoneyGiveaways();

    List<GiveawayDto> getAllVipMoneyGiveaways();

}
