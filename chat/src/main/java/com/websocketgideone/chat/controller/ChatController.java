package com.websocketgideone.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Реализуем метод для отправки сообщений в чат
    // Так @SendTo указывает куда будет отправлено сообщение @Payload
    // @MessageMapping указывает с какого пути мы будем обрабатывать сообщения @Payload
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    // Метод добавления пользователей в чат по пути
    //@MessageMapping
    // Так @SendTo указывает куда будет отправлено сообщение @Payload
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Добавления пользователя в WebSocket сессию
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return chatMessage;
    }


}
