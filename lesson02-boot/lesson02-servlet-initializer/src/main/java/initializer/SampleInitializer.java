package initializer;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;
import java.util.Set;
import java.util.logging.Logger;

@HandlesTypes(SampleInit.class)
public class SampleInitializer implements ServletContainerInitializer {
    private static final Logger log = Logger.getLogger(ServletContainerInitializer.class.getName());

    @Override
    public void onStartup(Set<Class<?>> sampleInitClasses, ServletContext ctx) throws ServletException {
        log.info("onStartup called");
        for (Class<?> sampleInitClass : sampleInitClasses) {
            init(sampleInitClass, ctx);
        }
    }

    private void init(Class<?> sampleInitClass, ServletContext ctx) throws ServletException {
        try {
            SampleInit sampleInit = (SampleInit) sampleInitClass.newInstance();
            sampleInit.init(ctx);
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new ServletException("Unable to process " + sampleInitClass, ex);
        }
    }
}
