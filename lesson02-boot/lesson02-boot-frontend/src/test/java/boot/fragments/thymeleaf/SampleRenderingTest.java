package boot.fragments.thymeleaf;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericApplicationContext;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

public class SampleRenderingTest {
    private ITemplateEngine templateEngine;
    
    @BeforeEach
    void init() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("classpath:templates/samples/");
        resolver.setSuffix(".html");
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(resolver);
        templateEngine = engine;
    }
    
    @Test
    void testHello() {
        System.out.println(render("hello", Collections.singletonMap("name", "Spring Course")));
    }

    private String render(String template, Map<String, Object> variables) {
        Context ctx = new Context(Locale.GERMANY, variables);
        String rendered = templateEngine.process(template, ctx);
        return rendered;
    }

}
