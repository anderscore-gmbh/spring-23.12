package boot.frontend.ui;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import boot.frontend.model.Task;
import boot.frontend.model.Task.State;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.context.webmvc.SpringWebMvcThymeleafRequestContext;
import org.thymeleaf.spring6.naming.SpringContextVariableNames;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

public class ThymeleafRenderingTest {
    private ITemplateEngine templateEngine;
    private GenericWebApplicationContext applicationContext;
    
    @BeforeEach
    void init() {
        applicationContext = new GenericWebApplicationContext();
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("classpath:templates/");
        resolver.setSuffix(".html");
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(resolver);
        templateEngine = engine;
    }
    
    @Test
    void testTaskList() {
        List<Task> tasks = List.of(createTask(), createTask(), createTask());
        System.out.println(render("tasks/taskList", Collections.singletonMap("selections", tasks)));
    }

    @Test
    void testCreateOrUpdateForm() {
        Map<String, Object> model = new HashMap<>();
        model.put("task", createTask());
        model.put("states", List.of("OPEN", "STARTED", "DONE"));
        var req = new MockHttpServletRequest();
        req.setAttribute(RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
        var reqctx = new RequestContext(req, model);
        var thctx = new SpringWebMvcThymeleafRequestContext(reqctx, req);
        model.put(SpringContextVariableNames.THYMELEAF_REQUEST_CONTEXT, thctx);
        System.out.println(render("tasks/createOrUpdateForm", model));
    }

    private Task createTask() {
        Task task = new Task();
        task.setId(42L);
        task.setDescription("Beschreibung");
        task.setDateDue(new Date());
        task.setState(State.STARTED);
        return task;
    }

    private String render(String template, Map<String, Object> variables) {
        Context ctx = new Context(Locale.GERMANY, variables);
        String rendered = templateEngine.process(template, ctx);
        return rendered;
    }

}
