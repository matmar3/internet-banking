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
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        return new DispatcherServlet(servletAppContext);
    }

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
