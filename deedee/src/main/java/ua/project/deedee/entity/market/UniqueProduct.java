package ua.project.deedee.entity.market;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.entity.BasicProductInfo;

@Entity
@Table(name = "unique_products")
@Getter
@Setter
@NoArgsConstructor
public class UniqueProduct extends BasicProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private UniqueProductInfo productInfo;

    public UniqueProduct(String title, String description,
                         Integer money, Integer vipMoney, UniqueProductInfo productInfo) {
        super(title, description, money, vipMoney);
        this.productInfo = productInfo;
    }

}
