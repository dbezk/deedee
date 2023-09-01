package ua.project.deedee.entity.character;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.project.deedee.entity.BasicEntityCharacteristic;
import ua.project.deedee.entity.market.UniqueProduct;

import java.util.List;

@Entity
@Table(name = "characters")
@Getter
@Setter
@NoArgsConstructor
public class DeeDeeCharacter extends BasicEntityCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String image;

    public DeeDeeCharacter(
            String name, String image,
            int power, int health, int speed) {
        super(power, health, speed);
        this.name = name;
        this.image = image;
    }
}
