package com.labelflow.changeorder.common.persistence.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

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
