package ua.project.deedee.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.project.deedee.dto.auth.AuthSuccessLoginDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.jwt.JwtUtils;
import ua.project.deedee.service.IAuthService;
import ua.project.deedee.service.IEntityConverterService;
import ua.project.deedee.ws.service.IWSService;
import ua.project.deedee.service.implemetation.UserService;

import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthConsumer {

    @Value(value = "${jwt.issuer.url}")
    private String jwtIssuerUrl;

    final IAuthService authService;
    final IEntityConverterService converterService;

    final IWSService wsService;

    final ObjectMapper objectMapper;

    final JwtUtils jwtUtils;

    final Map<Integer, DeeDeeUserPersonalInfoDto> mobileAuthedList;

    @KafkaListener(
            topics = "${kafka.auth.done.topic.name}",
            groupId = "${kafka.auth.topic.group-id}"
    )
    public void listenAuthCode(String authSuccessLoginDtoString) {
        log.info("success auth received '{}'", authSuccessLoginDtoString);
        try {
            AuthSuccessLoginDto authSuccessLoginDto = objectMapper.readValue(authSuccessLoginDtoString, AuthSuccessLoginDto.class);
            var newUser = authService.registerNewUser(authSuccessLoginDto.getAuthTelegramUserDto());
            DeeDeeUserPersonalInfoDto returnData = converterService.convertUserPersonalInfoToDto(newUser);
            var accessToken = jwtUtils.generateUserAccessToken(String.valueOf(newUser.getId()),
                    jwtIssuerUrl);
            returnData.setAccessToken(accessToken);
            mobileAuthedList.put(authSuccessLoginDto.getAuthLoginCodeDto().getCode(), returnData);
            wsService.sendLoginData(authSuccessLoginDto.getAuthLoginCodeDto().getSessionId(),
                    returnData);
        } catch (JsonProcessingException e) {
            log.error("error when converting dto '{}'", authSuccessLoginDtoString);
        }
    }

}
