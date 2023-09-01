package ua.project.deedee.service;

import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.enums.BalanceUpdateType;

public interface IUserBalanceService {

    public boolean checkUserBalance(DeeDeeUser user,
                                    Integer money, Integer vipMoney);

    public void updateBalance(DeeDeeUser user,
                              Integer money, Integer vipMoney,
                              BalanceUpdateType updateType);

}
