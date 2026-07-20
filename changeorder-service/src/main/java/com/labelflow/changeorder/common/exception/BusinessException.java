package com.labelflow.changeorder.common.exception;

public abstract class BusinessException extends RuntimeException {
    protected BusinessException(String message) {
        super(message);
    }
}