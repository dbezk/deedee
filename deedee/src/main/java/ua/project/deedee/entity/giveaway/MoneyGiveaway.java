package ua.project.deedee.entity.giveaway;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.enums.GiveawayType;

@Entity
@Table(name = "money_giveaways")
@Getter
@Setter
@NoArgsConstructor
public class MoneyGiveaway extends Giveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public MoneyGiveaway(String title,
                         String description,
                         Integer money, int hoursLimit) {
        super(title, description,
                money, null, GiveawayType.MONEY_GIVEAWAY, hoursLimit);
    }

}
