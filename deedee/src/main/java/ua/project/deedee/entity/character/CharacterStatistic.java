package ua.project.deedee.entity.character;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ua.project.deedee.entity.BasicEntityCharacteristic;
import ua.project.deedee.entity.market.UniqueProduct;

@Entity
@Table(name = "characters_statistics")
@Getter
@Setter
public class CharacterStatistic extends BasicEntityCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private UniqueProduct uniqueProduct;

    public CharacterStatistic() {
        super(0, 0, 0);
        this.uniqueProduct = null;
    }
    public CharacterStatistic(int power, int health, int speed) {
        super(power, health, speed);
    }

    public CharacterStatistic(int power, int health,
                              int speed, UniqueProduct uniqueProduct) {
        super(power, health, speed);
        this.uniqueProduct = uniqueProduct;
    }

}
