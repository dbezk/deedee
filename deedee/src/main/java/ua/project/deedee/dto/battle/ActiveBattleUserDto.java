package ua.project.deedee.dto.battle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.project.deedee.dto.character.CharacterStatisticDto;

@Getter
@Setter
@Builder
public class ActiveBattleUserDto {

    private String firstName;
    private String lastName;
    private String avatar;

    private int rating;

    private CharacterStatisticDto userStatistic;


}
