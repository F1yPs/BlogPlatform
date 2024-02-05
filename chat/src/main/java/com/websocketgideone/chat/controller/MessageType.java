package com.websocketgideone.chat.controller;

public enum MessageType {

    CHAT, // Активный пользователь(отправляет сообщение в чат)
    JOIN, // Пользователь подключается к чату
    LEAVER // Пользователь отключается из чата
}
