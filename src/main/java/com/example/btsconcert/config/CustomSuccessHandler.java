package com.example.btsconcert.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(CustomSuccessHandler.class.getName());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        logger.info("User roles on login success: " + roles);

        if (roles.contains("ROLE_MANAGER")) {
            response.sendRedirect("/manager/concerts");
        } else if (roles.contains("ROLE_FAN")) {
            response.sendRedirect("/concerts");
        } else {
            logger.warning("User has no recognized role, redirecting to /");
            response.sendRedirect("/");
        }
    }
}
