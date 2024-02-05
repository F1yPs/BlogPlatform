package com.websocketgideone.chat.config;

import com.websocketgideone.chat.controller.ChatMessage;
import com.websocketgideone.chat.controller.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;

    /* Метод прослушивает события соединения с WebSocket сервера
    При отключении соединения с WebSocket сервера происходит обработку
    события SessionDisconnectEvent и мы оповещаем пользователей, который прослушивают
    "/topic/public"


    * */
    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVER)
                    .sender(username)
                    .build();
            // отправляем на указый путь сообщения о отключении пользователя
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
