package ua.project.deedee.entity.market;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.entity.BasicEntityCharacteristic;

@Entity
@Table(name = "static_products_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaticProductInfo extends BasicEntityCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public StaticProductInfo(Integer power, Integer health, Integer speed) {
        super(power, health, speed);
    }

}
