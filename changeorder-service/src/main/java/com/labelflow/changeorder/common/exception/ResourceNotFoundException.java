package com.labelflow.changeorder.common.exception;

import com.labelflow.changeorder.common.exception.BusinessException;

public class ResourceNotFoundException extends BusinessException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
