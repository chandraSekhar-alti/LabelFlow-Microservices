package com.labelflow.labelchange.sequence.impl;

import com.labelflow.labelchange.sequence.SequenceService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

    private final EntityManager entityManager;

    @Override
    public Long getNextLabelChangeSequence() {

        Number value = (Number) entityManager
                .createNativeQuery("SELECT nextval('label_change_sequence')")
                .getSingleResult();

        return value.longValue();
    }

    @Override
    public Long getNextChangeOrderSequence() {
        Number value = (Number) entityManager
                .createNativeQuery("SELECT nextval('change_order_sequence')")
                .getSingleResult();

        return value.longValue();
    }
}