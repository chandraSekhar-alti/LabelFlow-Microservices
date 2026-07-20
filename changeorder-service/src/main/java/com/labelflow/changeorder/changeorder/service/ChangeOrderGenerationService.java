package com.labelflow.changeorder.changeorder.service;

import common.event.changeorder.LabelChangeReadyForChangeOrderEvent;

public interface ChangeOrderGenerationService {

  void generate(LabelChangeReadyForChangeOrderEvent event);
}
