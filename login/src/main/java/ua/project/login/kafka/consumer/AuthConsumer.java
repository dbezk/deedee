package ua.project.login.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.project.login.constants.AuthCodeStatus;
import ua.project.login.dto.AuthLoginCodeDto;
import ua.project.login.model.LoginCode;
import ua.project.login.service.CodeService;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthConsumer {

    CodeService codeService;

    ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${kafka.auth.topic.name}",
            groupId = "${kafka.auth.topic.group-id}"
    )
    public void listenAuthCode(String loginCodeDto) {
        log.info("login code received '{}'", loginCodeDto);
        try {
            AuthLoginCodeDto convertedLoginCodeDto =
                    objectMapper.readValue(loginCodeDto, AuthLoginCodeDto.class);
            LoginCode loginCode = objectMapper.convertValue(convertedLoginCodeDto, LoginCode.class);
            loginCode.setChatId(null);
            loginCode.setCodeStatus(AuthCodeStatus.UNUSED);
            codeService.addCode(loginCode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
