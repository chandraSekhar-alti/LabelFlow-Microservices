package com.labelflow.changeorder.common.exception;

import com.labelflow.changeorder.common.exception.BusinessException;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
