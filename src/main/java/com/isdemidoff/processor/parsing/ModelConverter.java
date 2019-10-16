package com.isdemidoff.processor.parsing;

import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.OutputModel;

/**
 * По сути своей является фабрикой для {@link OutputModel}.
 */
public class ModelConverter {

    public static OutputModel convertModel(InputModel model, Long line, String filename) {
        return new OutputModel(
                model.getOrderId(),
                model.getAmount(),
                model.getComment(),
                filename,
                line,
                "OK"
        );
    }

    public static OutputModel errorModel(Long line, String filename, String error) {
        return new OutputModel(
                null,
                null,
                null,
                filename,
                line,
                error
        );
    }

}
