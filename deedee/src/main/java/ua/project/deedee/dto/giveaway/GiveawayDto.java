package ua.project.deedee.dto.giveaway;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GiveawayDto {

    private Long id;
    private String title;
    private String description;

}
