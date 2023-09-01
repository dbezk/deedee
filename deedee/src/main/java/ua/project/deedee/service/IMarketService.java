package ua.project.deedee.service;

import ua.project.deedee.entity.market.StaticProduct;
import ua.project.deedee.entity.market.UniqueProduct;
import ua.project.deedee.entity.user.DeeDeeUser;

import java.util.List;

public interface IMarketService {

    public StaticProduct getStaticProduct(Long productId);

    public UniqueProduct getUniqueProduct(Long productId);

    public List<StaticProduct> getAllStaticProducts();

    public List<UniqueProduct> getAllUniqueProducts();

    public void buyStaticProduct(Long id, Long botId);

    public void buyUniqueProduct(Long id, Long botId);

}
