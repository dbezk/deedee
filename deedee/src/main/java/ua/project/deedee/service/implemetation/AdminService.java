package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.entity.character.CharacterStatistic;
import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;
import ua.project.deedee.entity.market.StaticProduct;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.entity.user.UserStatistic;
import ua.project.deedee.repository.DeeDeeUserRepository;
import ua.project.deedee.repository.character.DeeDeeCharacterRepository;
import ua.project.deedee.repository.giveaway.MoneyGiveawayRepository;
import ua.project.deedee.repository.giveaway.VipMoneyGiveawayRepository;
import ua.project.deedee.repository.market.StaticProductRepository;
import ua.project.deedee.repository.market.UniqueProductRepository;
import ua.project.deedee.service.IAdminService;
import ua.project.deedee.ws.service.IWSService;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminService implements IAdminService {

    IWSService iwsService;

    DeeDeeUserRepository deeDeeUserRepository;

    DeeDeeCharacterRepository deeDeeCharacterRepository;

    StaticProductRepository staticProductRepository;
    UniqueProductRepository uniqueProductInfoRepository;

    MoneyGiveawayRepository moneyGiveawayRepository;
    VipMoneyGiveawayRepository vipMoneyGiveawayRepository;

    @Override
    public void addBot(String firstName,
                       String lastName,
                       String avatar,
                       int money,
                       int vipMoney,
                       DeeDeeCharacter botCharacter) {
        var bot = DeeDeeUser.builder()
                .firstName(firstName)
                .lastName(lastName)
                .chatId(null)
                .avatar(avatar)
                .money(money)
                .vipMoney(vipMoney)
                .isBot(true)
                .build();
        bot.setDeeDeeCharacter(botCharacter);
        bot.setCharacterStatistic(
                new CharacterStatistic(botCharacter.getPower(),
                        botCharacter.getHealth(), botCharacter.getSpeed())
        );
        bot.setUserStatistic(new UserStatistic());
        bot.setChatMessages(new ArrayList<>());
        deeDeeUserRepository.save(bot);
        iwsService.connectBot(String.valueOf(bot.getId()));
        // TODO: Online list - save bot or not and connect bot randomly ?
    }

    @Override
    public void addCharacter(DeeDeeCharacter character) {
        deeDeeCharacterRepository.save(character);
    }

    @Override
    public void addStaticProduct(StaticProduct product) {
        staticProductRepository.save(product);
    }

    @Override
    public void addUniqueProduct(UniqueProduct product) {
        uniqueProductInfoRepository.save(product);
    }

    @Override
    public void addMoneyGiveaway(MoneyGiveaway moneyGiveaway) {
        moneyGiveawayRepository.save(moneyGiveaway);
    }

    @Override
    public void addVipMoneyGiveaway(VipMoneyGiveaway vipMoneyGiveaway) {
        vipMoneyGiveawayRepository.save(vipMoneyGiveaway);
    }
}
