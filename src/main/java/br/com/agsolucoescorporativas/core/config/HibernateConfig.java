package br.com.agsolucoescorporativas.core.config;

import br.com.agsolucoescorporativas.core.persistence.tenant.GroupTenantResolver;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HibernateConfig implements HibernatePropertiesCustomizer {

    private final GroupTenantResolver groupTenantResolver;

    public HibernateConfig(GroupTenantResolver groupTenantResolver) {
        this.groupTenantResolver = groupTenantResolver;
    }

    @Override
    public void customize(Map<String, Object> properties) {
        properties.put(
                "hibernate.tenant_identifier_resolver",
                groupTenantResolver
        );
    }
}