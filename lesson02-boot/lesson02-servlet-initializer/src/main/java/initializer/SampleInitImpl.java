package initializer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import java.util.logging.Logger;

public class SampleInitImpl implements SampleInit {
    private static final Logger log = Logger.getLogger(SampleInitImpl.class.getName());

    @Override
    public void init(ServletContext sc) {
        log.info("init called");
        ServletRegistration.Dynamic registration = sc.addServlet("sample", new SampleServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/sample");
    }
}
