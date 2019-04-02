package com.keybds.springblog.configuration;

import com.keybds.springblog.constants.UrlConstants;
import com.keybds.springblog.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] IGNORED_STATIC_RESOURCE_LIST = new String[] {
        "/webjars/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/assets/**"
    };

    private static final String[] PERMIT_URL_LIST = new String[] {
        UrlConstants.HOME,
        UrlConstants.SIGN_IN, UrlConstants.SIGN_UP,
        UrlConstants.PUBLIC_ARTICLE.concat("/**"), UrlConstants.PUBLIC_ARTICLES_LIST.concat("/**"),
    };

    @Autowired
    @Qualifier(value = "accountService")
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(PERMIT_URL_LIST).permitAll()
                .antMatchers(IGNORED_STATIC_RESOURCE_LIST).permitAll()
                .antMatchers(UrlConstants.ADMIN_PREFIX.concat("/**")).hasAnyAuthority(RoleEnum.ADMIN.getValue())
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage(UrlConstants.SIGN_IN).successHandler(authenticationSuccessHandler)
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(IGNORED_STATIC_RESOURCE_LIST);
    }

}
