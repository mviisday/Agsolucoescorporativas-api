package br.com.agsolucoescorporativas.core.persistence.tenant;


import java.util.UUID;

public final class TenantContext {

    private static final ThreadLocal<UUID> CURRENT_TENANT =
            new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenantId(UUID groupId) {
        if (groupId == null) {
            throw new IllegalArgumentException("O identificador do tenant não pode ser nulo.");
        }
        CURRENT_TENANT.set(groupId);
    }

    public static UUID getTenantId() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
