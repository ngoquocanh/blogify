package com.keybds.blogify.configuration;

import com.keybds.blogify.constants.UrlConstants;
import com.keybds.blogify.enums.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to "
                    + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String targetUrl;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isUser(roles)) {
            targetUrl = UrlConstants.PUBLIC_ARTICLES_LIST;
        } else if (isAdmin(roles)) {
            targetUrl = UrlConstants.ADMIN_POSTS_LIST;
        } else {
            targetUrl = UrlConstants.ACCESS_DENIED;
        }

        return targetUrl;
    }

    protected static boolean isUser(List<String> roles) {
        return (roles.contains(RoleEnum.USER.getValue())) ? true : false;
    }

    protected static boolean isAdmin(List<String> roles) {
        return (roles.contains(RoleEnum.ADMIN.getValue())) ? true : false;
    }
}
