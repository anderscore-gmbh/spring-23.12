package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

public class PrimaryTest {

    @Configuration
    static class Config {

        // tag::x[]
        @Bean
        @Primary
        Engine gasEngine() {
            return new Engine("gas", 68);
        }

        @Bean
        Engine dieselEngine() {
            return new Engine("diesel", 72);
        }

        @Bean
        Car car(Engine engine) {
           var car = new Car();
           car.setEngine(engine);
           return car;
        }
        // end::x[]
    }
    
    @Test
    void test() {
        var ctx = new AnnotationConfigApplicationContext(Config.class);
        Car car = ctx.getBean(Car.class);
        assertThat(car.getEngine()).hasToString("gas (68 kw)");
    }
}
