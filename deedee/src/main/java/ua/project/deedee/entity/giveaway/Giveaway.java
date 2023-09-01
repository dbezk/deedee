package ua.project.deedee.entity.giveaway;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.project.deedee.enums.GiveawayType;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Giveaway {

    private String title;
    private String description;

    private Integer money;
    private Integer vipMoney;

    private GiveawayType giveawayType;

    private int hoursLimit;

    public Giveaway(String title,
                    String description,
                    Integer money,
                    Integer vipMoney,
                    GiveawayType giveawayType, int hoursLimit) {
        this.title = title;
        this.description = description;
        this.money = money;
        this.vipMoney = vipMoney;
        this.giveawayType = giveawayType;
        this.hoursLimit = hoursLimit;
    }

}
