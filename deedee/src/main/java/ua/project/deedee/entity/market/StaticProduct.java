package ua.project.deedee.entity.market;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.entity.BasicProductInfo;

@Entity
@Table(name = "static_products")
@Getter
@Setter
@NoArgsConstructor
public class StaticProduct extends BasicProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private StaticProductInfo productInfo;

    public StaticProduct(String title, String description,
                         Integer money, Integer vipMoney, StaticProductInfo productInfo) {
        super(title, description, money, vipMoney);
        this.productInfo = productInfo;
    }

}
