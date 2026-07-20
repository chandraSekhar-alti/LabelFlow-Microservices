package com.labelflow.changeorder.common.exception;

import com.labelflow.changeorder.common.exception.BusinessException;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String message) {
        super(message);
    }
}
