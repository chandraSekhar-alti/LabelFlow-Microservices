package com.labelflow.labelchange.common.persistence.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        /*
         * For now, I was returning SYSTEM,
         *
         * Once JWT Authentication is implemented
         * we will return logged-in user's email
         * from spring security context
         */

        return Optional.of("SYSTEM");
    }
}
