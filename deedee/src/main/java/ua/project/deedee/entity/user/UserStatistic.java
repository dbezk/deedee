package ua.project.deedee.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users_statistics")
@Data
public class UserStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int winBattles;

    private int loseBattles;

    private int winMoney;

    private int winVipMoney;

    private int rating;

    @OneToOne(mappedBy = "userStatistic")
    @JsonIgnore
    private DeeDeeUser user;

    public UserStatistic() {
        this.winBattles = 0;
        this.loseBattles = 0;
        this.winMoney = 0;
        this.winVipMoney = 0;
        this.rating = 0;
    }
}
