package ua.project.deedee.dto.user;

import jakarta.annotation.Nullable;
import lombok.Data;
import ua.project.deedee.dto.character.CharacterStatisticDto;
import ua.project.deedee.dto.character.DeeDeeCharacterDto;
import ua.project.deedee.dto.market.ProductDto;
import ua.project.deedee.entity.market.UniqueProduct;

import java.util.List;

@Data
public class DeeDeeUserPersonalInfoDto {

    private Long id;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    private String avatar;

    @Nullable
    private DeeDeeCharacterDto deeDeeCharacter;

    @Nullable
    private CharacterStatisticDto characterStatistic;

    @Nullable
    private UserStatisticDto userStatistic;

    private List<ProductDto> uniqueProducts;

    private int money;

    private int vipMoney;

    @Nullable
    String accessToken;

}