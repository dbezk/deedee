package ua.project.deedee.repository.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.deedee.entity.market.UniqueProductInfo;

@Repository
public interface UniqueProductInfoRepository extends JpaRepository<UniqueProductInfo, Long> {
}
