package com.elice.meetstudy.domain.audit;

import com.elice.meetstudy.util.EntityFinder;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfiguration {

    private final EntityFinder entityFinder;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl(entityFinder);
    }
}
