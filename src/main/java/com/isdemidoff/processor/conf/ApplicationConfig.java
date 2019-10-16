package com.isdemidoff.processor.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isdemidoff.processor.converter.FileProcessor;
import com.isdemidoff.processor.converter.ModelProcessor;
import com.isdemidoff.processor.parsing.CsvModelParser;
import com.isdemidoff.processor.parsing.JsonModelParser;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = {"com.isdemidoff.processor"})
public class ApplicationConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelProcessor csvModelProcessor(
            ObjectMapper objectMapper,
            CsvModelParser csvModelParser
    ) {
        return new ModelProcessor(objectMapper, csvModelParser);
    }

    @Bean
    public ModelProcessor jsonModelProcessor(
            ObjectMapper objectMapper,
            JsonModelParser jsonModelParser
    ) {
        return new ModelProcessor(objectMapper, jsonModelParser);
    }

    @Bean
    public FileProcessor fileProcessor(DefaultListableBeanFactory bf) {
        Map<String, ModelProcessor> map = bf
                .getBeansOfType(ModelProcessor.class)
                .values()
                .stream()
                .collect(Collectors.toMap(
                        mp -> mp.supportingType(),
                        mp -> mp
                ));
        return new FileProcessor(map);
    }
}
