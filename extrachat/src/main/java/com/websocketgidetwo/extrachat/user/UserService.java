package com.websocketgidetwo.extrachat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // Метод для сохранения/подключения пользователя
    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }

    // Метод отключения пользователя с проверкой его в БД
    public void disconnect(User user) {
        var storedUser = repository.findById(user.getNickName())
                .orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }
}
