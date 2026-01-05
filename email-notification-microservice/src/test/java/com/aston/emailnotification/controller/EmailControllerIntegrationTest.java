package com.aston.emailnotification.controller;

import com.aston.emailnotification.mail.EmailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("api")
class EmailControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private EmailServiceImpl emailService;


    @Test
    @DisplayName("When send email about created user then success")
    void whenSendSimpleEmailAboutCreatedUser_thenSuccess() throws Exception {

        String email = "test1@gmail.com";
        String name = "testName1";
        String subject = "Информация об успешном создании аккаунта";
        String message = "Здравствуйте, " + name + "! Ваш аккаунт на сайте был успешно создан.";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/email/simple-email/type-operation-create")
                        .param("user-email", email)
                        .param("user-name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailService, timeout(5000).times(1))
                .sendSimpleEmail(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());
        // С помощью созданного ArgumentCaptor перехватываем поступившие в метод аргументы и записываем их значения
        // в переменные emailCaptor, subjectCaptor, messageCaptor.

        Assertions.assertEquals(email, emailCaptor.getValue());
        Assertions.assertEquals(subject, subjectCaptor.getValue());
        Assertions.assertEquals(message, messageCaptor.getValue());
    }


    @Test
    @DisplayName("When send email about deleted user then success")
    void whenSendSimpleEmailAboutDeletedUser_thenSuccess() throws Exception {

        String email = "test2@gmail.com";
        String name = "testName2";
        String subject = "Информация об успешном удалении аккаунта";
        String message = "Здравствуйте, " + name + "! Ваш аккаунт был удален.";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/email/simple-email/type-operation-delete/{user-email}/{user-name}", email, name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailService, timeout(5000).times(1))
                .sendSimpleEmail(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());
        // С помощью созданного ArgumentCaptor перехватываем поступившие в метод аргументы и записываем их значения
        // в переменные emailCaptor, subjectCaptor, messageCaptor.

        Assertions.assertEquals(email, emailCaptor.getValue());
        Assertions.assertEquals(subject, subjectCaptor.getValue());
        Assertions.assertEquals(message, messageCaptor.getValue());
    }
}