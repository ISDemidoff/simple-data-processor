package com.isdemidoff.processor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutputModel {
    private final Long orderId;
    private final Long amount;
    // Странно, что в формате выходных данных не было этого поля
//    private final String currency;
    private final String comment;
    private final String filename;
    private final Long line;
    private final String result;
}
//{“id”:1, ”amount”:100, ”comment”:”оплата заказа”, ”filename”:”orders.csv”, ”line”:1, ”result”:”OK” }
