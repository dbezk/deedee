package ua.project.deedee.dto.market;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.entity.BasicProductInfo;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto extends BasicProductInfo {

    private Long id;

    public ProductDto(Long id,
                      String title,
                      String description,
                      Integer money,
                      Integer vipMoney) {
        super(title, description, money, vipMoney);
        this.id = id;
    }

}
