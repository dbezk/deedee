package ua.project.deedee.ws;

import com.sun.security.auth.UserPrincipal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import ua.project.deedee.data.OnlineUserData;
import ua.project.deedee.jwt.JwtUtils;
import ua.project.deedee.ws.service.IWSService;

import java.util.List;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    JwtUtils jwtUtils;

    String NATIVE_HEADER_NAME = "access_token";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor != null) {
            if(accessor.getCommand() == StompCommand.CONNECT) {
                if(checkTokenHeader(accessor)) {
                    String userId = jwtUtils.getTokenSubject(getTokenFromHeader(accessor));
                    UsernamePasswordAuthenticationToken user =
                            new UsernamePasswordAuthenticationToken(accessor.getSessionId(), userId);
                    accessor.setUser(user);
                } else {
                    accessor.setUser(new UsernamePasswordAuthenticationToken(accessor.getSessionId(), null));
                }
                log.info("connected sessionId = {}", getSessionId(accessor));
            }
            if(accessor.getCommand() == StompCommand.DISCONNECT) {
                log.info("disconnected sessionId = {}", getSessionId(accessor));
            }
        }
        return message;
    }

    public String getTokenFromHeader(StompHeaderAccessor accessor) {
        return accessor.getNativeHeader(NATIVE_HEADER_NAME).get(0);
    }

    public boolean checkTokenHeader(StompHeaderAccessor accessor) {
        return accessor.getNativeHeader(NATIVE_HEADER_NAME) != null;
    }

    public String getSessionId(StompHeaderAccessor accessor) {
        return accessor.getSessionId();
    }

}
