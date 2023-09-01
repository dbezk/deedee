package ua.project.deedee.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class DoneBattleData {

    private Long battleId;
    private LocalDateTime endsAt;

}
