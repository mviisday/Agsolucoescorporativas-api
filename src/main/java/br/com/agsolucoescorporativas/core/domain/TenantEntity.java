package br.com.agsolucoescorporativas.core.domain;

import jakarta.persistence.Column;
import org.hibernate.annotations.TenantId;

import java.util.UUID;

public abstract class TenantEntity extends BaseEntity {

    @TenantId
    @Column(name = "group_id", nullable = false, updatable = false)
    private UUID groupId;

    public UUID getGroupId() {
        return groupId;
    }
}
