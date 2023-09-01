package ua.project.deedee.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import ua.project.deedee.entity.character.DeeDeeCharacter;
import ua.project.deedee.entity.character.CharacterStatistic;
import ua.project.deedee.entity.chat.ChatMessage;
import ua.project.deedee.entity.market.UniqueProduct;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeeDeeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    private Long chatId;

    private String avatar;

    @Nullable
    @OneToOne
    private DeeDeeCharacter deeDeeCharacter;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    private CharacterStatistic characterStatistic;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    private UserStatistic userStatistic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UniqueProduct> uniqueProducts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<ChatMessage> chatMessages;

    private int money;

    private int vipMoney;

    private boolean isBot;

}
