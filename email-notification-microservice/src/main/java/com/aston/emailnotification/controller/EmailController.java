package com.aston.emailnotification.controller;

import com.aston.emailnotification.mail.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@Slf4j
@RequestMapping("/email")
@AllArgsConstructor
@Profile("api")
public class EmailController {

    EmailService emailService;

    @GetMapping(value = "/simple-email/type-operation-create")
    public ResponseEntity<String> sendSimpleEmailAboutCreatedUser(
            @RequestParam("user-email") String email,
            @RequestParam("user-name") String name
    ) {

        try {
            emailService.sendSimpleEmail(email, "Информация об успешном создании аккаунта",
                    "Здравствуйте, " + name + "! Ваш аккаунт на сайте был успешно создан.");
        } catch (RuntimeException e) {
            log.error("Ошибка при отправке электронного письма: ", e);
            return new ResponseEntity<>("Не удается отправить электронное письмо", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Пожалуйста, проверьте свою почту", HttpStatus.OK);
    }


    @GetMapping(value = "/simple-email/type-operation-delete/{user-email}/{user-name}")
    public ResponseEntity<String> sendSimpleEmailAboutDeletedUser(
            @PathVariable("user-email") String email,
            @PathVariable("user-name") String name) {

        try {
            emailService.sendSimpleEmail(email, "Информация об успешном удалении аккаунта",
                    "Здравствуйте, " + name + "! Ваш аккаунт был удален.");
        } catch (MailException e) {
            log.error("Ошибка при отправке электронного письма: ", e);
            return new ResponseEntity<>("Не удается отправить электронное письмо", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Пожалуйста, проверьте свою почту", HttpStatus.OK);
    }



//    @GetMapping(value = "/simple-order-email/{user-email}")
//    public ResponseEntity<String> sendEmailAttachment(@PathVariable("user-email") String email) {
//
//        try {
//            emailService.sendEmailWithAttachment(email, "Order Confirmation",
//                    "Thanks for your recent order", "classpath:purchase_order.png");
//        } catch (MessagingException | FileNotFoundException mailException) {
//            log.error("Error while sending out email..{} {}", mailException.getStackTrace(), mailException.fillInStackTrace());
//            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity<>("Please check your inbox for order confirmation", HttpStatus.OK);
//    }

}
