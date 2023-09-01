package ua.project.deedee.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginCodeDto {

    Integer code;
    LocalDateTime expiresAt;
    String sessionId;

}
