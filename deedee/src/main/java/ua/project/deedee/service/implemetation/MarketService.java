package ua.project.deedee.service.implemetation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.entity.market.StaticProduct;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.enums.BalanceUpdateType;
import ua.project.deedee.exception.EntityNotFoundException;
import ua.project.deedee.exception.IllegalTypeException;
import ua.project.deedee.exception.NotEnoughBalanceException;
import ua.project.deedee.repository.market.StaticProductRepository;
import ua.project.deedee.repository.market.UniqueProductRepository;
import ua.project.deedee.service.ICharacterService;
import ua.project.deedee.service.IMarketService;
import ua.project.deedee.service.IUserBalanceService;
import ua.project.deedee.service.IUserService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MarketService implements IMarketService {

    IUserService userService;
    ICharacterService characterService;
    IUserBalanceService userBalanceService;

    StaticProductRepository staticProductRepository;
    UniqueProductRepository uniqueProductRepository;

    @Override
    public StaticProduct getStaticProduct(Long productId) {
        return staticProductRepository.findById(productId).orElseThrow(() -> {
            throw new EntityNotFoundException(
                    String.format("Product with id %d not found.", productId)
            );
        });
    }

    @Override
    public UniqueProduct getUniqueProduct(Long productId) {
        return uniqueProductRepository.findById(productId).orElseThrow(() -> {
            throw new EntityNotFoundException(
                    String.format("Product with id %d not found.", productId)
            );
        });
    }

    @Override
    public List<StaticProduct> getAllStaticProducts() {
        return staticProductRepository.findAll();
    }

    @Override
    public List<UniqueProduct> getAllUniqueProducts() {
        return uniqueProductRepository.findAll();
    }

    @Override
    public void buyStaticProduct(Long id, Long botId) {
        StaticProduct product = getStaticProduct(id);
        var user = userService.getUserById(botId != null ? botId : userService.getUserId());
        if (userBalanceService.checkUserBalance(user, product.getMoney(), product.getVipMoney())) {
            userBalanceService.updateBalance(user, product.getMoney(), product.getVipMoney(), BalanceUpdateType.CHARGE);
            characterService.updateCharacterStatistic(user.getCharacterStatistic(),
                    product.getProductInfo().getPower(),
                    product.getProductInfo().getHealth(),
                    product.getProductInfo().getSpeed());
            userService.saveUser(user);
        } else {
            throw new NotEnoughBalanceException("You don't have enough money.");
        }
    }

    @Override
    public void buyUniqueProduct(Long id, Long botId) {
        UniqueProduct product = getUniqueProduct(id);
        var user = userService.getUserById(botId != null ? botId : userService.getUserId());
        if(!user.getUniqueProducts().contains(product)) {
            if (userBalanceService.checkUserBalance(user, product.getMoney(), product.getVipMoney())) {
                userBalanceService.updateBalance(user, product.getMoney(), product.getVipMoney(), BalanceUpdateType.CHARGE);
                characterService.updateCharacterStatistic(user.getCharacterStatistic(),
                        product.getProductInfo().getPower(),
                        product.getProductInfo().getHealth(),
                        product.getProductInfo().getSpeed());
                user.getUniqueProducts().add(product);
                userService.saveUser(user);
            } else {
                throw new NotEnoughBalanceException("You don't have enough money.");
            }
        } else {
            throw new IllegalTypeException("You already have this product.");
        }
    }


}
