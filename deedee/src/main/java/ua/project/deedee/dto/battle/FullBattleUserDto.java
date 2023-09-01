package ua.project.deedee.dto.battle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FullBattleUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String avatar;
    private Integer startHealth;

}
