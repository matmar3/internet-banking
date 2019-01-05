package cz.zcu.kiv.pia.martinm.internetbanking.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Web MVC configuration file.
 *
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
@Configuration
@EnableWebMvc
@ComponentScan("cz.zcu.kiv.pia.martinm.internetbanking.controller")
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configures path for static resources e.g. css, js, images, ...
     *
     * @param reg - instance that stores registrations of resource handlers for serving static resources
     *            such as images, css files and others through Spring MVC
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry reg) {
        reg.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
        reg.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        reg.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
    }

    /**
     * Configures location of JSP templates which can be displayed using Spring MVC Controller
     *
     * @return instance of {@link InternalResourceViewResolver} which is view class for all
     * views that can be specified via {@link org.springframework.web.servlet.view.UrlBasedViewResolver}
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Creates bean of ModelMapper that can be injected in application
     *
     * @return instance of ModelMapper, that can be used to map attributes of some object
     * to attributes of another object
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Creates bean of MessageSource that can be injected in application
     *
     * @return instance of MessageSource which provides methods to access messages in file
     * messages.properties on classpath.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(60 * 60); // reload messages every hour
        return messageSource;
    }

}
