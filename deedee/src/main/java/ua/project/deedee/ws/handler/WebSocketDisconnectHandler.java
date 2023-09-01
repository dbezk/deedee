package ua.project.deedee.ws.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ua.project.deedee.ws.service.IWSService;

import static java.util.Optional.ofNullable;

@Slf4j
@Getter
@Setter
public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {

    IWSService iwsService;

    public WebSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate) {
        super();
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        var user = readUser(event);
        log.info("User {} disconnected from session id {}",
                user.getCredentials(), user.getPrincipal());
        if(user.getCredentials() != null) {
            iwsService.disconnectUser((String) user.getCredentials());
        }
    }

    Authentication readUser(SessionDisconnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        return (Authentication) SimpMessageHeaderAccessor.getUser(headers);
    }

}
