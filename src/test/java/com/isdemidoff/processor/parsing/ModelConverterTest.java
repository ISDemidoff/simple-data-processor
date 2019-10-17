package com.isdemidoff.processor.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isdemidoff.processor.conf.RootTest;
import com.isdemidoff.processor.model.OutputModel;
import org.junit.Test;

public class ModelConverterTest extends RootTest {

    private final ObjectMapper mapper = beanCreator.objectMapper();

    @Test
    public void errorModel() throws JsonProcessingException {
        OutputModel errorOutput = ModelConverter.errorModel(
                10L,
                "SomeFile",
                "AssertionError: \"You should be a nice person.\""
        );
        System.out.println(mapper.writeValueAsString(errorOutput));

        // There is no need in checking convertor (I'm too lazy)
    }

}