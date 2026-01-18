package com.aston.email_notification_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailNotificationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailNotificationMicroserviceApplication.class, args);
    }

}