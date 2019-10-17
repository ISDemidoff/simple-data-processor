package com.isdemidoff.processor.converter;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

@AllArgsConstructor
public class FileProcessor {
    private final Map<String, ModelProcessor> availableParsers;

    private ModelProcessor resolveDataType(Path file) throws IOException {
        String[] parts = file.getFileName().toString().split("\\.");
        String extension = parts[parts.length - 1].toLowerCase();

        if (availableParsers.containsKey(extension)) {
            return availableParsers.get(extension);
        } else {
            throw new IOException(String.format("Unsupported input file type %s in file: %s", extension, file));
        }
    }

    private void processFile(Path file) {
        try {
            resolveDataType(file).processFile(file);
        } catch (IOException e) {
            // This goes to standard err output!
            e.printStackTrace();
        }
    }

    public void processFiles(String[] files) {
        Stream.of(files).parallel().map(str -> Paths.get(str)).forEach(
                file -> processFile(file)
        );
    }

}
