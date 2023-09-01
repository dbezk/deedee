package ua.project.deedee.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.project.deedee.dto.chat.ChatMessageDto;
import ua.project.deedee.service.IChatService;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/chat")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatApi {

    IChatService chatService;

    @PostMapping(value = "/newMessage")
    public ResponseEntity<?> sendNewMessage(
            @RequestBody ChatMessageDto chatMessageDto
            ) {
        chatService.saveNewMessage(chatMessageDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/chat/newMessage").toUriString());
        return ResponseEntity.created(uri).build();
    }

}
