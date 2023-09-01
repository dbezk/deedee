package ua.project.login.dto;

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
