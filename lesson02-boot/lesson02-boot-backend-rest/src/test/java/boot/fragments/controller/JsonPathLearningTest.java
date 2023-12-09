package boot.fragments.controller;

import org.junit.Test;
import org.springframework.test.util.JsonPathExpectationsHelper;

public class JsonPathLearningTest {
    private String task;

    @Test
    public void testJsonPath() {
        task = "{\"description\":\"Learn using MockMvc\",\"dateDue\":\"2018-11-30\",\"state\":\"STARTED\"}";
        check("$");
        check("$..state");
        check("$.state");
        check("state");
    }

    @Test
    public void testArray() {
        task = "[{\"id\":1,\"description\":\"REST mit datum\",\"dateDue\":\"2018-10-03T00:00:00.000+0000\",\"state\":\"STARTED\"}," +
                "{\"id\":2,\"description\":\"REST mit datum\",\"dateDue\":\"2018-10-03T00:00:00.000+0000\",\"state\":\"STARTED\"}]";
        check("$");
        check("$..state");
        check("$[0].state");
        check("$[0]state");
        check("$[1]state");
        check("$[*].state");
        check("$..id");
        check("$[-1:]");
    }

    private void check(String expr) {
        JsonPathExpectationsHelper jp = new JsonPathExpectationsHelper(expr);
        Object result = jp.evaluateJsonPath(task);
        System.out.println(expr + " -> " + result);
    }
}
