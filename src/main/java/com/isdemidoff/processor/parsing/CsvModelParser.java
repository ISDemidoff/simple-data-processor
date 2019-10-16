package com.isdemidoff.processor.parsing;

import com.isdemidoff.processor.model.Currency;
import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.ParseException;
import com.opencsv.CSVParser;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Можно было и забиндить маппинг сразу в нужную модель (как json), но для этого придется усложнять дтошку.
 */
@Component
public class CsvModelParser implements ModelParser {

    @Override
    public InputModel parseString(String line) throws ParseException {
        // Default constuctor is OK!
        CSVParser parser = new CSVParser();

        String[] strings;
        try {
            strings = parser.parseLine(line);
        } catch (IOException e) {
            throw new ParseException("Incorrect comma-separated format in line", e);
        }

        if (strings.length != 4) {
            throw new ParseException("Incorrect number of columns, should be 4");
        }

        final Long orderId;
        try {
            orderId = Long.parseLong(strings[0]);
        } catch (NumberFormatException e) {
            throw new ParseException("Incorrect orderId format, should be valid base 10 number", e);
        }

        final Long amount;
        try {
            amount = Long.parseLong(strings[1]);
        } catch (NumberFormatException e) {
            throw new ParseException("Incorrect amount format, should be valid base 10 number", e);
        }

        final Currency currency;
        try {
            currency = Currency.valueOf(strings[2]);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Incorrect currency, should be alphabetic universal code", e);
        }

        final String comment = strings[3];

        return new InputModel(orderId, amount, currency, comment);
    }

    @Override
    public String supportingType() {
        return "csv";
    }
}
