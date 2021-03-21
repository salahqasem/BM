package com.brightminds.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("**login**").permitAll()
            .antMatchers("/admin/statement/**")
            .hasRole("admin")
            .antMatchers("/statement/**")
            .hasAnyRole("admin", "user")
            .antMatchers("/homePage").authenticated()
            .and()
            .sessionManagement()
            .maximumSessions(1)
            .sessionRegistry(sessionRegistry())
            .maxSessionsPreventsLogin(true)
            .expiredUrl("/logout")
            .and()
            .and()
            .authorizeRequests()
            .and().formLogin().loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/homePage", true)
            .and()
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .addLogoutHandler((request, response, authentication) ->
                sessionRegistry().getAllSessions(authentication.getPrincipal(), false).forEach(
                    s -> s.expireNow()
                ))
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder())
            .withUser("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("admin")
            .and()
            .withUser("user")
            .password(passwordEncoder().encode("user"))
            .roles("user");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
