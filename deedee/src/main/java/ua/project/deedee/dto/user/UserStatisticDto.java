package ua.project.deedee.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserStatisticDto {

    private int winBattles;
    private int loseBattles;
    private int winMoney;
    private int winVipMoney;
    private int rating;



}
