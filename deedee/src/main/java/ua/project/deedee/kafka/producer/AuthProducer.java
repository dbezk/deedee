package ua.project.deedee.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ua.project.deedee.dto.auth.AuthLoginCodeDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthProducer {

    @Value(value = "${kafka.auth.topic.name}")
    private String authTopicName;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> authKafkaTemplate;

    private final Random random;

    private final Map<Integer, DeeDeeUserPersonalInfoDto> mobileAuthedList;
    public AuthLoginCodeDto createAndSendLoginCode(String sessionId) {
        var authLoginCodeDto = AuthLoginCodeDto.builder()
                .code(random.nextInt(1_000_000) + 1_000_000)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .sessionId(sessionId)
                .build();
        try {
            String convertedLoginCode = objectMapper.writeValueAsString(authLoginCodeDto);
            authKafkaTemplate.send(authTopicName, convertedLoginCode).get();
            mobileAuthedList.put(authLoginCodeDto.getCode(), null);
        } catch (InterruptedException e) {
            log.warn("error when when code '{}' to kafka with error '{}'",
                    authLoginCodeDto, e);
            Thread.currentThread().interrupt();
        } catch (JsonProcessingException | ExecutionException e) {
            log.error("error when sending or converting login code '{}'",
                    authLoginCodeDto);
        }
        return authLoginCodeDto;
    }

}
