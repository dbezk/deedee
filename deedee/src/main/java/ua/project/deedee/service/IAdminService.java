package ua.project.deedee.service;

import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.entity.giveaway.MoneyGiveaway;
import ua.project.deedee.entity.giveaway.VipMoneyGiveaway;
import ua.project.deedee.entity.market.StaticProduct;
import ua.project.deedee.entity.market.UniqueProduct;

public interface IAdminService {

    void addBot(String firstName,
                String lastName,
                String avatar,
                int money,
                int vipMoney,
                DeeDeeCharacter botCharacter);
    void addCharacter(DeeDeeCharacter character);

    void addStaticProduct(StaticProduct product);

    void addUniqueProduct(UniqueProduct product);

    void addMoneyGiveaway(MoneyGiveaway moneyGiveaway);

    void addVipMoneyGiveaway(VipMoneyGiveaway vipMoneyGiveaway);

}
