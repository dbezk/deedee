package ua.project.deedee.entity.market;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.entity.BasicEntityCharacteristic;
import ua.project.deedee.enums.ProductEvent;

@Entity
@Table(name = "unique_products_info")
@Getter
@Setter
@NoArgsConstructor
public class UniqueProductInfo extends BasicEntityCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ProductEvent event;

    public UniqueProductInfo(Integer power, Integer health,
                             Integer speed, ProductEvent event) {
        super(power, health, speed);
        this.event = event;
    }
}
