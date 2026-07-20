package com.labelflow.labelchange.sequence;

public interface SequenceService {
    Long getNextLabelChangeSequence();

    Long getNextChangeOrderSequence();
}
