package ua.project.deedee.service;

import ua.project.deedee.dto.chat.ChatMessageDto;

import java.util.List;

public interface IChatService {

    public List<ChatMessageDto> getLastChatMessages();

    public void saveNewMessage(ChatMessageDto messageDto);

    public boolean isSpamMessage(String userId);

}
