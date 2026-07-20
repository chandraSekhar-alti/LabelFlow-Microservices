package com.labelflow.labelchange.sequence.impl;

import com.labelflow.labelchange.sequence.BusinessCodeGenerator;
import com.labelflow.labelchange.sequence.SequenceService;
import java.time.Year;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessCodeGeneratorImpl implements BusinessCodeGenerator {

    private final SequenceService sequenceService;

    @Override
    public String generateLabelChangeNumber() {

        long sequence = sequenceService.getNextLabelChangeSequence();

        return String.format("LC-%d-%06d", Year.now().getValue(), sequence);
    }

    @Override
    public String generateChangeOrderNumber() {
        int year = Year.now().getValue();
        long sequence = sequenceService.getNextChangeOrderSequence();

        return String.format("CO-%d-%06d", year, sequence);
    }


}