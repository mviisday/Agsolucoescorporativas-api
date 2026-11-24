package br.com.agsolucoescorporativas.core.persistence.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GroupTenantResolver
        implements CurrentTenantIdentifierResolver<UUID> {

    @Override
    public UUID resolveCurrentTenantIdentifier() {

        UUID groupId = TenantContext.getTenantId();

        if (groupId == null) {
            throw new IllegalStateException(
                    "Nenhum tenant foi definido para a requisição atual."
            );
        }

        return groupId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}