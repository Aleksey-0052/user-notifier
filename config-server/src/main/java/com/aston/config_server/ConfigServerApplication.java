package com.aston.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    // Аннотация @EnableConfigServer активирует сервер конфигурации Spring Cloud, который позволяет приложению получать
    // конфигурации из внешнего источника, в данном случае из Git репозитория.

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}