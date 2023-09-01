package ua.project.deedee.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthTelegramUserDto {

    String firstName;
    String lastName;
    Long chatId;
    String avatar;

}
