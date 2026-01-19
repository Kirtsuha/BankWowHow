package org.example;

import org.example.service.OperationsConsoleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationsConsoleListener consoleListener = applicationContext.getBean(OperationsConsoleListener.class);
        consoleListener.logic_switcher();
    }
}