package ua.project.deedee.config.ws;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import ua.project.deedee.ws.handler.WebSocketConnectHandler;
import ua.project.deedee.ws.handler.WebSocketDisconnectHandler;
import ua.project.deedee.ws.service.IWSService;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WebSocketHandlersConfig<S extends Session> {

    IWSService iwsService;

    @Bean
    public WebSocketConnectHandler<S> webSocketConnectHandler(
            SimpMessageSendingOperations messagingTemplate) {
        WebSocketConnectHandler<S> webSocketConnectHandler =
                new WebSocketConnectHandler<>(messagingTemplate);
        webSocketConnectHandler.setIwsService(iwsService);
        return webSocketConnectHandler;
    }

    @Bean
    public WebSocketDisconnectHandler<S> webSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate) {
        WebSocketDisconnectHandler<S> webSocketDisconnectHandler =
                new WebSocketDisconnectHandler<>(messagingTemplate);
        webSocketDisconnectHandler.setIwsService(iwsService);
        return webSocketDisconnectHandler;
    }

}
