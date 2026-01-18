package com.aston.email_notification_microservice.mail;

public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);

//    void sendEmailWithAttachment(final String toAddress, final String subject,
//                                 final String message, final String attachment)
//            throws MessagingException, FileNotFoundException;
}
