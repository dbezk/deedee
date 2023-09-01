package ua.project.deedee.service.implemetation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.project.deedee.dto.chat.ChatMessageDto;
import ua.project.deedee.entity.chat.ChatMessage;
import ua.project.deedee.exception.ChatSpamMessageException;
import ua.project.deedee.exception.EmptyMessageException;
import ua.project.deedee.repository.chat.ChatMessageRepository;
import ua.project.deedee.service.IChatService;
import ua.project.deedee.service.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatService implements IChatService {

    int MIN_MESSAGE_LENGTH = 5;
    int MAX_MESSAGE_LENGTH = 200;

    ChatMessageRepository messageRepository;

    IUserService userService;

    ObjectMapper objectMapper;

    @Override
    public List<ChatMessageDto> getLastChatMessages() {
        Pageable pageable = PageRequest.of(0, 20,
                Sort.by("id").descending());
        Page<ChatMessage> pages = messageRepository.findAll(pageable);
        return pages.getContent().stream()
                .map(cm -> objectMapper.convertValue(cm, ChatMessageDto.class))
                .map(cm -> {
                    var userAvatar = userService.getUserById(Long.parseLong(cm.getSenderId())).getAvatar();
                    cm.setAvatar(userAvatar);
                    return cm;
                }).toList();
    }

    @Override
    public void saveNewMessage(ChatMessageDto messageDto) {
        if(!isSpamMessage(messageDto.getSenderId())) {
            if(messageDto.getMessageText().length() >= MIN_MESSAGE_LENGTH &&
                messageDto.getMessageText().length() <= MAX_MESSAGE_LENGTH) {
                messageRepository.save(ChatMessage.builder()
                        .senderId(Long.parseLong(messageDto.getSenderId()))
                        .messageText(messageDto.getMessageText())
                        .sentAt(LocalDateTime.now())
                        .build());
            } else {
                throw new EmptyMessageException(
                        String.format("Message range is [%d;%d] characters.",
                                MIN_MESSAGE_LENGTH, MAX_MESSAGE_LENGTH)
                );
            }
        } else {
            throw new ChatSpamMessageException("Spam detected. Send one message per 5 seconds.");
        }
    }

    @Override
    public boolean isSpamMessage(String senderId) {
        var message =
                messageRepository.findFirstBySenderIdOrderByIdDesc(Long.parseLong(senderId));
        if(message != null) {
            return message.getSentAt().plusSeconds(5).isAfter(LocalDateTime.now());
        }
        return false;
    }



}
