package com.isdemidoff.processor.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.ParseException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JsonModelParser implements ModelParser {

    private final ObjectMapper mapper;

    @Override
    public InputModel parseString(String line) throws ParseException {
        try {
            return mapper.readValue(line, InputModel.class);
        } catch (IOException e) {
            throw new ParseException("Could not read json value from given string", e);
        }
    }

    @Override
    public String supportingType() {
        return "json";
    }
}
