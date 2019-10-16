package com.isdemidoff.processor.parsing;

import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.ParseException;

public interface ModelParser {

    /**
     * Получение следующей строки ввода в формате входных данных.
     */
    InputModel parseString(String line) throws ParseException;

    String supportingType();

}
