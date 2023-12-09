package config.fragments;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:config-${platform:prod}.properties")
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Value("#{ 1 + 1 }")
    private int value;

    @Value("#{ ${server.port} + 1 }")
    private int portValue;

    @Autowired
    private Environment env;

    // tag::default[]
    @Value("${service.port:9080}")
    private int servicePort;

    @Value("${service.url:http://localhost:${service.port:9080}/}")
    private String serviceUrl;
    // end::default[]

    // tag::value[]
    @Value("${server.port}")
    private int serverPort;

    @Value("http://localhost:${server.port}/")
    private String serverUrl;

    @Bean
    SomeService someService(@Value("${debug}") boolean debug) {
        // end::value[]
        log.info("value=" + value);
        log.info("portValue=" + portValue);
        log.info("servicePort=" + servicePort);
        log.info("serviceUrl=" + serviceUrl);
        log.info("serverPort=" + serverPort);
        log.info("serverUrl=" + serverUrl);
        log.info("debug=" + debug);

        int port = env.getProperty("service.port", Integer.class, 8079);
        log.info("service.port=" + port);
        return new SomeService(42);
    }

    // tag::configurer[]
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    // end::configurer[]

    @Bean
    SomeService expressionLanguageSamples(
            // tag::el[]
            @Value("#{ 'http://localhost:' + (${server.port} + 1) }") String urlValue,
            @Value("#{ T(java.lang.Math).random() * 100.0 }") double randomValue,
            @Value("#{ someService.serviceValue }") int beanProperty,
            @Value("#{ systemProperties['service.port'] ?: 8888 }") int systemProperty, // Elvis Operator
            @Value("#{ {1, 3, 5, 7} }") List<Integer> listValue,
            @Value("#{ {banane: 'gelb', tomate: 'rot', gurke: 'grün'} }") Map<String, String> mapValue,
            @Value("#{ new int[]{3,2,1} }") int[] arrayValue, @Value("#{ 'abcde'.substring(1, 4) }") String subString
    // end::el[]
    ) {
        log.info("urlValue=" + urlValue);
        log.info("randomValue=" + randomValue);
        log.info("beanProperty=" + beanProperty);
        log.info("systemProperty=" + systemProperty);
        log.info("listValue=" + listValue);
        log.info("mapValue=" + mapValue);
        log.info("arrayValue=" + Arrays.toString(arrayValue));
        log.info("subString=" + subString);
        return new SomeService(999);
    }
}
