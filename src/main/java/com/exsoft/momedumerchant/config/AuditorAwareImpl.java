package com.exsoft.momedumerchant.config;

import com.exsoft.momedumerchant.domain.Staff;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    public static final String ANONYMOUS = "anonymousUser";

    @Override
    public Optional<String> getCurrentAuditor() {

        if (null != SecurityContextHolder.getContext().getAuthentication() && getUsername()) {
            return Optional.ofNullable(((Staff) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername());
        }
        return Optional.ofNullable(System.getProperties().get("user.name").toString());

    }

    private boolean getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !username.equalsIgnoreCase(ANONYMOUS);
    }

}
