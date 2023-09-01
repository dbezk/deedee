package ua.project.deedee.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSuccessLoginDto {

    AuthLoginCodeDto authLoginCodeDto;

    AuthTelegramUserDto authTelegramUserDto;

}
