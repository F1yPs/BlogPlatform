package com.websocketgidetwo.extrachat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage chatMessage
    ) {
        ChatMessage savedMSg = chatMessageService.save(chatMessage);

        // join/queue/messages
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                new ChatNotification(
                        savedMSg.getId(),
                        savedMSg.getSenderId(),
                        savedMSg.getRecipientId(),
                        savedMSg.getContent()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId
    ) {
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, recipientId));
    }
}
