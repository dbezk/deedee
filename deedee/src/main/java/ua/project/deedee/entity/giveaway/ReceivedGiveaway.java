package ua.project.deedee.entity.giveaway;

import jakarta.persistence.*;
import lombok.*;
import ua.project.deedee.entity.user.DeeDeeUser;
import ua.project.deedee.enums.GiveawayType;

import java.time.LocalDateTime;

@Entity
@Table(name = "received_giveaways")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivedGiveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private DeeDeeUser user;

    private LocalDateTime timestamp;

    @Enumerated(value = EnumType.STRING)
    private GiveawayType giveawayType;

}
