package com.isdemidoff.processor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InputModel {
    private final Long orderId;
    private final Long amount;
    private final Currency currency;
    private final String comment;
}
