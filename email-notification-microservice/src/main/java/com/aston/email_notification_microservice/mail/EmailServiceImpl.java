package com.aston.email_notification_microservice.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    public JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("alexsemen666653@gmail.com");
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        try {
            emailSender.send(simpleMailMessage);
        } catch (MailException e) {
            log.error("Ошибка при отправке электронного письма: ", e);
            throw new RuntimeException(e);
        }

        // В методе создаётся объект SimpleMailMessage, которому задаются получатель, тема и текст письма. Затем письмо
        // отправляется с помощью объекта JavaMailSender.
    }

    // Отправление письма с файлом
//    @Override
//    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment)
//            throws MessagingException, FileNotFoundException {
//
//        MimeMessage mimeMessage = emailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//        messageHelper.setFrom("alexsemen666653@gmail.com");
//        messageHelper.setTo(toAddress);
//        messageHelper.setSubject(subject);
//        messageHelper.setText(message);
//        FileSystemResource file= new FileSystemResource(ResourceUtils.getFile(attachment));
//        messageHelper.addAttachment("File name 1", file);
//        emailSender.send(mimeMessage);

        // В методе создаётся объект MimeMessage, которому задаются получатель, тема и текст письма. Затем добавляется
        // вложение с помощью объекта FileSystemResource. После этого письмо отправляется с помощью объекта JavaMailSender.
//    }

}
