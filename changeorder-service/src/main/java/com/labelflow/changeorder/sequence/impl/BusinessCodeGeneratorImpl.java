package com.labelflow.changeorder.sequence.impl;

import com.labelflow.changeorder.common.config.TimeConfig;
import com.labelflow.changeorder.sequence.BusinessCodeGenerator;
import com.labelflow.changeorder.sequence.SequenceService;
import java.time.Year;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessCodeGeneratorImpl implements BusinessCodeGenerator {

    private final TimeConfig timeConfig;

    private final SequenceService sequenceService;

    @Override
    public String generateChangeOrderNumber() {
        int year = timeConfig.currentYear().getValue();
        long sequence = sequenceService.getNextChangeOrderSequence();

        return String.format("CO-%d-%06d", year, sequence);
    }


}