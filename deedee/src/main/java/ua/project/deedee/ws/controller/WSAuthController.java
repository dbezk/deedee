package ua.project.deedee.ws.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import ua.project.deedee.dto.auth.AuthLoginCodeDto;
import ua.project.deedee.dto.user.DeeDeeUserPersonalInfoDto;
import ua.project.deedee.kafka.producer.AuthProducer;

import java.security.Principal;
import java.util.Map;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WSAuthController {

    SimpMessagingTemplate simpMessagingTemplate;

    AuthProducer authProducer;

    @MessageMapping(value = "/newAuth")
    @SendToUser(value = "/topic/authCode")
    public AuthLoginCodeDto sendNewAuthCode(final Principal principal) {
        return authProducer.createAndSendLoginCode(principal.getName());
    }

}
