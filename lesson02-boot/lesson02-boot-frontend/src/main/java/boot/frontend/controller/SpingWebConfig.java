package boot.frontend.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpingWebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(dateFormatter());
    }

    @Bean
    public DateFormatter dateFormatter() {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setIso(ISO.DATE);
        return dateFormatter;
    }
}
