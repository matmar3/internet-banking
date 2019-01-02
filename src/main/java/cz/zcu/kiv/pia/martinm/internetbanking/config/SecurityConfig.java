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
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                    .logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
