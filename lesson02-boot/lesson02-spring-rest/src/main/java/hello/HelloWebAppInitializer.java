package hello;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// tag::code[]
public class HelloWebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Config-Klassen für Root-ApplicationContext
        return new Class[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Config-Klassen für Servlet-ApplicationContext
        return new Class[] {HelloConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // Servlet-Mappings für das DispatcherServlet
        return new String[] {"/"};
    }
}
// end::code[]