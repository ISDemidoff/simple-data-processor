package com.isdemidoff.processor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class InputModel {
    private final long orderId;
    // We could make this value double, but it's unacceptable on fintech
    private final long amount;
    private final Currency currency;
    private final String comment;

    @JsonCreator
    public InputModel(
            @JsonProperty("orderId") final long orderId,
            @JsonProperty("amount") final long amount,
            @JsonProperty("currency") final Currency currency,
            @JsonProperty("comment") final String comment
    ) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }
}
