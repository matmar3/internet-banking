package cz.zcu.kiv.pia.martinm.internetbanking.config;

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
                .csrf()
                    .disable()
                .httpBasic()
                    .and()
                .authorizeRequests()
                    .antMatchers("/internet-banking/**").hasAnyRole("USER")
                    .antMatchers("/management/**").hasAuthority("ADMIN")
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/login")
                    .successForwardUrl("/")
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
