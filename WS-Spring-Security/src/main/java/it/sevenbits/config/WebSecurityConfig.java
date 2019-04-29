package it.sevenbits.config;

import it.sevenbits.web.security.filter.HeaderJwtAuthFilter;
import it.sevenbits.web.security.filter.JwtAuthFilter;
import it.sevenbits.web.security.provider.JwtAuthenticationProvider;
import it.sevenbits.web.security.service.JsonWebTokenService;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.security.settings.JwtSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Created to configure a global security. Annotated with EnableWebSecurity
 * to enable web security support by Spring Security and provide Spring
 * MVC integration. Also extends WebSecurityConfigurerAdapter to redefine some
 * its methods to set some specific things for web security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtTokenService jwtTokenService;

    /**
     * The constructor.
     *
     * @param jwtTokenService is needed to be set in other objects.
     */
    public WebSecurityConfig(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * The method determines whether a path must be protected or not.
     *
     * @param http is needed to set security settings
     * @throws Exception is thrown if something goes wrong
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.sessionManagement().disable();
        http.requestCache().disable();
        http.anonymous();

        RequestMatcher loginPageMatcher = new AntPathRequestMatcher("/signin");
        RequestMatcher notLoginPageMatcher = new NegatedRequestMatcher(loginPageMatcher);

        JwtAuthFilter authFilter = new HeaderJwtAuthFilter(notLoginPageMatcher);
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);

        http
                .authorizeRequests().antMatchers("/signin").permitAll()
                .and()
                .authorizeRequests().antMatchers("/users/**").hasAuthority("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/whoami").hasAuthority("USER")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    /**
     * The method creates a PasswordEncoder's bean. Is needed to encrypt objects,
     * for example passwords.
     *
     * @return a specialized type of PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The method creates a JwtTokenService's bean. Is needed to work with tokens,
     * for example create or parse them.
     *
     * @param settings some settings for the object.
     * @return a specialized type of JwtTokenService.
     */
    @Bean
    @Qualifier("jwtTokenService")
    JwtTokenService jwtTokenService(final JwtSettings settings) {
        return new JsonWebTokenService(settings);
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JwtAuthenticationProvider(jwtTokenService));
    }
}
