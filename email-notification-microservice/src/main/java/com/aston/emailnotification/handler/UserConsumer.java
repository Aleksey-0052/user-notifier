package com.aston.emailnotification.handler;

import com.aston.core.UserCreatedDeletedEvent;
import com.aston.emailnotification.mail.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile("!api")
@Slf4j
@AllArgsConstructor
@Component
@KafkaListener(topics = "user-created-deleted-events-topic")
public class UserConsumer {

    private static final String CREATED_USER = "UserCreated";
    private static final String DELETED_USER = "UserDeleted";

    private final EmailService emailService;


    @KafkaHandler
    public void handle(UserCreatedDeletedEvent event) {

        if(event.getType().equals(CREATED_USER)) {
           emailService.sendSimpleEmail(event.getEmail(), "Информация об успешном создании аккаунта",
                   "Здравствуйте, " + event.getName() + "! Ваш аккаунт на сайте был успешно создан.");
            log.info("A user creation event has been received: {}", event.getName());

        } else if(event.getType().equals(DELETED_USER)) {
            emailService.sendSimpleEmail(event.getEmail(), "Информация об успешном удалении аккаунта",
                    "Здравствуйте, " + event.getName() + "! Ваш аккаунт был удален.");
            log.info("A user deletion event has been received: {}", event.getName());
        }
    }

}
