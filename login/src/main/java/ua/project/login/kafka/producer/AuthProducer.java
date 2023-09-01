package ua.project.login.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.project.login.dto.AuthLoginCodeDto;
import ua.project.login.dto.AuthSuccessLoginDto;
import ua.project.login.dto.AuthTelegramUserDto;
import ua.project.login.model.LoginCode;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthProducer {

    @Value(value = "${kafka.auth.done.topic.name}")
    private String authDoneTopicName;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendSuccessAuth(Message message, LoginCode loginCode, String avatarPath) {
        try {
            var telegramUser = AuthTelegramUserDto.builder()
                    .chatId(message.getChatId())
                    .firstName(message.getChat().getFirstName())
                    .lastName(message.getChat().getLastName())
                    .avatar(avatarPath).build();
            var loginInfo = AuthLoginCodeDto.builder()
                    .code(loginCode.getCode())
                    .sessionId(loginCode.getSessionId()).build();
            var successLoginDto = AuthSuccessLoginDto.builder()
                    .authLoginCodeDto(loginInfo)
                    .authTelegramUserDto(telegramUser).build();
            var convertedDto = objectMapper.writeValueAsString(successLoginDto);
            kafkaTemplate.send(authDoneTopicName, convertedDto).get();
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            log.error("Error when send response '{}' to topic '{}'",
                    loginCode, authDoneTopicName);
        }
    }

}
