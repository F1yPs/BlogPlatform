package com.websocketgideone.chat.controller;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String content; // Информационный текст
    private String sender; // Отправитель
    private MessageType type; // Тип сообщения
}
