package ua.project.login.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.project.login.constants.AuthCodeStatus;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCode {

    Integer code;
    LocalDateTime expiresAt;
    String sessionId;
    Long chatId;
    AuthCodeStatus codeStatus;

}
