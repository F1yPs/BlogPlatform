package com.websocketgidetwo.extrachat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Метод реализует создание простого брокера /user с префиксами приложения и пользователя назначения
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Создаем простой брокер /user
        registry.enableSimpleBroker("/user");
        // префикс назначения приложения /app
        registry.setApplicationDestinationPrefixes("/app");
        //Префикс назначения приложения /user
        registry.setUserDestinationPrefix("/user");
    }

    // Метод реализует создание endpoint /ws
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Создаем endpoint /ws - путь к WebSocket
        registry.addEndpoint("/ws")
                .withSockJS();
    }

    // Метод реализует добавления своего обьъекта конвертации сообщений
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        // Создаем экземпляр для приема свойств конвертаций
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        // MIME-называемый "media type", а иногда "content type"
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        // Создаем экземпляр конвертации Jackson(JSON)
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        // Создаем экземпляр ObjectMapper для сопоставления типов полей
        converter.setObjectMapper(new ObjectMapper());
        //Определяем тип распознавателя данных
        converter.setContentTypeResolver(resolver);
        // Добавляем в список конвертаций сообщений
        messageConverters.add(converter);

        return false; // Не хотим использовать стандартный конвектор(необходим наш)
    }
}
