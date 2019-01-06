package cz.zcu.kiv.pia.martinm.internetbanking;

import cz.zcu.kiv.pia.martinm.internetbanking.config.AppConfig;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Initialize servlet dispatcher and configure application context.
 *
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Loads application config class.
     *
     * @return application config class.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    /**
     * Loads servlet config class.
     *
     * @return servlet config class.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    /**
     * Returns servlet mappings.
     *
     * @return array of mappings
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * Creates dispatcher servlet from application context.
     *
     * @param servletAppContext - web application context
     * @return servlet
     */
    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        return new DispatcherServlet(servletAppContext);
    }

    /**
     * Configures application context on application start. Defines context listener,
     * HTML escape and servlet dispatcher. Dispatcher will be loaded when application start.
     *
     * @param container context container
     */
    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("cz.zcu.kiv.pia.martinm.internetbanking.config");

        container.addListener(new ContextLoaderListener(context));
        container.setInitParameter("defaultHtmlEscape", "true");

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", createDispatcherServlet(context));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
