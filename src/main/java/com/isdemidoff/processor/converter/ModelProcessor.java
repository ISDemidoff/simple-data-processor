package com.isdemidoff.processor.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.OutputModel;
import com.isdemidoff.processor.model.ParseException;
import com.isdemidoff.processor.parsing.ModelConverter;
import com.isdemidoff.processor.parsing.ModelParser;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class ModelProcessor {

    private final ObjectMapper mapper;
    private final ModelParser parser;

    /**
     * That's bad method, I could not extract one more subsystem (which should map OutputModel to string)...
     * <br/>
     * This method reads all lines from input file and process them as requested.
     *
     * @param inputFile    path to the file
     * @throws IOException If any input problem occurs
     */
    public void processFile(Path inputFile) throws IOException {
        long lineNumber = 0;
        for (String line : Files.readAllLines(inputFile)) {
            ++lineNumber;
            final long finalLineNumber = lineNumber;
            new Thread(() -> {
                InputModel input = null;
                OutputModel output = null;
                String result = null;
                try {
                    input = parser.parseString(line);
                    output = ModelConverter.convertModel(
                            input,
                            finalLineNumber,
                            inputFile.getFileName().toString()
                    );
                    result = mapper.writeValueAsString(output);
                } catch (ParseException | JsonProcessingException e) {
                    output = ModelConverter.errorModel(finalLineNumber, inputFile.getFileName().toString(), e.getMessage());
                }
                System.out.println(result);
            }).start();
        }
    }

    public String supportingType() {
        return parser.supportingType();
    }
}
