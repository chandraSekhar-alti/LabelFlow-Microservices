package com.labelflow.changeorder.common.exception;

import com.labelflow.changeorder.common.exception.BusinessException;

public class InvalidCredentialsException extends BusinessException {
  public InvalidCredentialsException(String message) {
    super(message);
  }
}
