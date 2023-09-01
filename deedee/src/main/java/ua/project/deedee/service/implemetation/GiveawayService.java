package ua.project.deedee.service.implemetation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.dto.giveaway.GiveawayDto;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;
import ua.project.deedee.entity.giveaway.ReceivedGiveaway;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;
import ua.project.deedee.enums.BalanceUpdateType;
import ua.project.deedee.enums.GiveawayType;
import ua.project.deedee.exception.EntityNotFoundException;
import ua.project.deedee.exception.GiveawayException;
import ua.project.deedee.repository.giveaway.MoneyGiveawayRepository;
import ua.project.deedee.repository.giveaway.ReceivedGiveawayRepository;
import ua.project.deedee.repository.giveaway.VipMoneyGiveawayRepository;
import ua.project.deedee.service.IGiveawayService;
import ua.project.deedee.service.IUserBalanceService;
import ua.project.deedee.service.IUserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiveawayService implements IGiveawayService {

    ReceivedGiveawayRepository receivedGiveawayRepository;

    MoneyGiveawayRepository moneyGiveawayRepository;
    VipMoneyGiveawayRepository vipMoneyGiveawayRepository;

    IUserService userService;
    IUserBalanceService userBalanceService;

    ObjectMapper objectMapper;

    @Override
    public void collectGiveaway(Long id, GiveawayType giveawayType) {
        var giveaway = giveawayType == GiveawayType.MONEY_GIVEAWAY ?
                moneyGiveawayRepository.findById(id).orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("Money giveaway with id %d not found", id));
                }) :
                vipMoneyGiveawayRepository.findById(id).orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("Vip money giveaway with id %d not found", id));
                });
        var user =
                userService.getUserById(userService.getUserId());
        ReceivedGiveaway lastReceive =
                receivedGiveawayRepository.findFirstByUserAndAndGiveawayTypeOrderByTimestampDesc(user, giveawayType);
        if(lastReceive == null || lastReceive.getTimestamp().plusHours(giveaway.getHoursLimit()).isBefore(LocalDateTime.now())) {
            userBalanceService.updateBalance(user, giveaway.getMoney(), giveaway.getVipMoney(), BalanceUpdateType.PAY);
            receivedGiveawayRepository.save(ReceivedGiveaway.builder()
                    .user(user)
                    .giveawayType(giveawayType)
                    .timestamp(LocalDateTime.now()).build());
        } else {
            throw new GiveawayException(
                    String.format("You already claimed this giveaway. You can collect this giveaway once at %d hours", giveaway.getHoursLimit()));
        }
    }

    @Override
    public List<GiveawayDto> getAllMoneyGiveaways() {
        return moneyGiveawayRepository.findAll().stream()
                .map(g -> objectMapper.convertValue(g, GiveawayDto.class))
                .toList();
    }

    @Override
    public List<GiveawayDto> getAllVipMoneyGiveaways() {
        return vipMoneyGiveawayRepository.findAll().stream()
                .map(g -> objectMapper.convertValue(g, GiveawayDto.class))
                .toList();
    }
}
