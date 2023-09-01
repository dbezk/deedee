package ua.project.deedee.repository.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.market.StaticProductInfo;

@Repository
public interface StaticProductInfoRepository extends JpaRepository<StaticProductInfo, Long> {
}