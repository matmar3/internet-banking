package cz.zcu.kiv.pia.martinm.internetbanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
@Configuration
@EnableWebMvc
@ComponentScan("cz.zcu.kiv.pia.martinm.internetbanking.controller")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry reg) {
        reg.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
        reg.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        reg.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

}
