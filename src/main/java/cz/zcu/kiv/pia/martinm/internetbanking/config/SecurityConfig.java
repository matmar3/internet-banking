package cz.zcu.kiv.pia.martinm.internetbanking.config;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures application security layer.
 *
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configures security layer. All requests are permitted except two authorized sections that require authentication
     * via login form. Specifies authentication process handlers and logout handler. For security exception
     * defines which view should be displayed.
     *
     * @param http - instance of HttpSecurity that allows configuring web based security for specific http requests.
     * @throws Exception - if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                    .and()
                .authorizeRequests()
                    .antMatchers("/ib/**").hasAuthority(User.Role.CUSTOMER.name())
                    .antMatchers("/admin/**").hasAuthority(User.Role.ADMIN.name())
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login-process")
                    .successForwardUrl("/role-check")
                    .failureForwardUrl("/login-failed")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    /**
     * Creates password encoder bean that can be injected and used to encrypt text.
     *
     * @return instance of password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
