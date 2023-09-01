package ua.project.deedee.dto.character;

import lombok.Getter;
import lombok.Setter;
import ua.project.deedee.dto.market.ProductDto;
import ua.project.deedee.entity.BasicEntityCharacteristic;
import ua.project.deedee.entity.market.UniqueProduct;

@Getter
@Setter
public class CharacterStatisticDto extends BasicEntityCharacteristic {

    private ProductDto uniqueProduct;

    public CharacterStatisticDto(Integer power,
                                 Integer health,
                                 Integer speed,
                                 ProductDto uniqueProduct) {
        super(power, health, speed);
        this.uniqueProduct = uniqueProduct;
    }

}
