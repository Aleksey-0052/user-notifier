package com.aston.email_notification_microservice.handler;

import com.aston.core.UserCreatedDeletedEvent;
import com.aston.email_notification_microservice.mail.EmailServiceImpl;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}")
class UserConsumerIntegrationTest {

    @MockitoBean
    private EmailServiceImpl emailService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockitoSpyBean
    private UserConsumer userConsumer;

    @Test
    void testUserCreatedEventsHandler_OnUserCreated_HandlesEvent() throws ExecutionException, InterruptedException {
        // Arrange
        String userId = "1";
        UserCreatedDeletedEvent event = new UserCreatedDeletedEvent();
        event.setType("UserCreated");
        event.setName("testName1");
        event.setEmail("test1@gmail.com");

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                "user-created-deleted-events-topic",
                userId,
                event
        );

        // Act
        kafkaTemplate.send(record).get();

        // Assert
        ArgumentCaptor<UserCreatedDeletedEvent> eventCaptor = ArgumentCaptor.forClass(UserCreatedDeletedEvent.class);

        verify(userConsumer, timeout(5000).times(1)).handle(eventCaptor.capture());
        // С помощью созданного ArgumentCaptor перехватываем поступивший в метод параметр и записываем его значение
        // в переменную recordCaptor

        Assertions.assertEquals(event.getType(), eventCaptor.getValue().getType());
        Assertions.assertEquals(event.getName(), eventCaptor.getValue().getName());
        Assertions.assertEquals(event.getEmail(), eventCaptor.getValue().getEmail());
    }


    @Test
    void testUserDeletedEventsHandler_OnUserCreated_HandlesEvent() throws ExecutionException, InterruptedException {
        // Arrange
        String userId = "1";
        UserCreatedDeletedEvent event = new UserCreatedDeletedEvent();
        event.setType("UserDeleted");
        event.setName("testName1");
        event.setEmail("test1@gmail.com");

        // Act
        kafkaTemplate.send("user-created-deleted-events-topic", userId, event).get();

        // Assert
        ArgumentCaptor<UserCreatedDeletedEvent> eventCaptor = ArgumentCaptor.forClass(UserCreatedDeletedEvent.class);

        verify(userConsumer, timeout(5000).times(1)).handle(eventCaptor.capture());
        // С помощью созданного ArgumentCaptor перехватываем поступивший в метод параметр и записываем его значение
        // в переменную recordCaptor

        Assertions.assertEquals(event.getType(), eventCaptor.getValue().getType());
        Assertions.assertEquals(event.getName(), eventCaptor.getValue().getName());
        Assertions.assertEquals(event.getEmail(), eventCaptor.getValue().getEmail());
    }

}