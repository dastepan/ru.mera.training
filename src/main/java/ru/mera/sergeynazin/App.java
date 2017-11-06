package ru.mera.sergeynazin;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        // FIXME: Nope!
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();

        ApplicationContext context = new ClassPathXmlApplicationContext();



    }
}
