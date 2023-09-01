package ua.project.deedee.entity.giveaway;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.enums.GiveawayType;

@Entity
@Table(name = "vip_money_giveaways")
@Getter
@Setter
@NoArgsConstructor
public class VipMoneyGiveaway extends Giveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public VipMoneyGiveaway(String title,
                         String description,
                         Integer vipMoney, int hoursLimit) {
        super(title, description,
                null, vipMoney, GiveawayType.VIP_MONEY_GIVEAWAY, hoursLimit);
    }

}
