package br.com.agsolucoescorporativas.core.persistence.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-Group-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String groupId = request.getHeader(TENANT_HEADER);

            if (groupId == null || groupId.isBlank()) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Tenant não informado."
                );
                return;
            }

            UUID tenantId;
            try {
                tenantId = UUID.fromString(groupId.trim());
            } catch (IllegalArgumentException exception) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Identificador de tenant inválido."
                );
                return;
            }

            TenantContext.setTenantId(tenantId);

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
