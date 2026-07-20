package com.labelflow.changeorder.common.exception;

import com.labelflow.changeorder.common.exception.BusinessException;

public class UnauthorizedException extends BusinessException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
