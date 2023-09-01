package ua.project.deedee.dto.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private Long id;

    private String senderId;

    private String avatar;

    private String messageText;

}
