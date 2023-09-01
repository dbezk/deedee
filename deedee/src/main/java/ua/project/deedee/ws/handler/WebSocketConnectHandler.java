package ua.project.deedee.ws.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import ua.project.deedee.ws.service.IWSService;

import static java.util.Optional.ofNullable;

@Slf4j
@Getter
@Setter
public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectEvent> {

    private IWSService iwsService;

    public WebSocketConnectHandler(SimpMessageSendingOperations messagingTemplate) {
        super();
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        var user = readUser(event);
        log.info("User {} connected to session id {}",
                user.getCredentials(), user.getPrincipal());
        if(user.getCredentials() != null) {
            iwsService.connectUser((String) user.getCredentials(),
                    (String) user.getPrincipal());
        }
    }

    String readSessionId(SessionConnectEvent event) {
        return SimpMessageHeaderAccessor.getSessionId(event.getMessage().getHeaders());
    }

    Authentication readUser(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        log.info("connected data = {} ", (Authentication) SimpMessageHeaderAccessor.getUser(headers));
        return (Authentication) SimpMessageHeaderAccessor.getUser(headers);
    }

}
