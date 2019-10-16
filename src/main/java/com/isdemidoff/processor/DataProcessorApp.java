package com.isdemidoff.processor;

import com.isdemidoff.processor.conf.ApplicationConfig;
import com.isdemidoff.processor.converter.FileProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DataProcessorApp {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class)) {

            FileProcessor fileProcessor = context.getBean(FileProcessor.class);

            fileProcessor.processFiles(args);
        }
    }
}
