package ua.project.deedee.data;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OnlineUserData {

    String sessionId;
    String userId;
    boolean isBotSession;

}
