package org.example;

import org.example.configuration.AppConfig;
import org.example.configuration.HibernateConfiguration;
import org.example.service.OperationsConsoleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                AppConfig.class,
                HibernateConfiguration.class
        );
        OperationsConsoleListener consoleListener = applicationContext.getBean(OperationsConsoleListener.class);
        consoleListener.logic_switcher();
    }
}