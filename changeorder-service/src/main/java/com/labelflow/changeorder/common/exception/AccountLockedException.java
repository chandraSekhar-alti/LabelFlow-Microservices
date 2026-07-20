package com.labelflow.changeorder.common.exception;

import com.labelflow.changeorder.common.exception.BusinessException;

public class AccountLockedException extends BusinessException {
    public AccountLockedException(String message) {
        super(message);
    }
}
