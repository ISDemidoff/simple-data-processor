package com.isdemidoff.processor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutputModel {
    private final long orderId;
    private final long amount;
    // Странно, что в формате выходных данных не было этого поля
//    private final String currency;
    private final String comment;
    private final String filename;
    private final long line;
    private final String result;
}
