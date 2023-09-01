package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.enums.BalanceUpdateType;
import ua.project.deedee.service.IUserBalanceService;
import ua.project.deedee.service.IUserService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class UserBalanceService implements IUserBalanceService {

    IUserService userService;


    @Override
    public boolean checkUserBalance(DeeDeeUser user, Integer money, Integer vipMoney) {
        if(money != null && vipMoney != null) {
            return (money <= user.getMoney() && vipMoney <= user.getVipMoney());
        } else if(money != null) {
            return money <= user.getMoney();
        } else if(vipMoney != null) {
            return vipMoney <= user.getVipMoney();
        }
        return false;
    }

    @Override
    public void updateBalance(DeeDeeUser user,
                              Integer money, Integer vipMoney,
                              BalanceUpdateType updateType) {
        if (money != null) {
            if(updateType == BalanceUpdateType.CHARGE) {
                user.setMoney(user.getMoney() - money);
            } else {
                user.setMoney(user.getMoney() + (updateType == BalanceUpdateType.MULTIPLY ? money * 2 : money));
            }
        }
        if (vipMoney != null) {
            if(updateType == BalanceUpdateType.CHARGE) {
                user.setVipMoney(user.getVipMoney() - vipMoney);
            } else {
                user.setVipMoney(user.getVipMoney() + (updateType == BalanceUpdateType.MULTIPLY ? vipMoney * 2 : vipMoney));
            }
        }
        userService.saveUser(user);
    }

}
