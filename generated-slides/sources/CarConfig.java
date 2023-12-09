package container.fragments;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// tag::beans[]
@Configuration
public class CarConfig {

    // Bean mit Namen 'firstCar'
    @Bean
    Car firstCar() {
        return new Car();
    }
    
    // Bean mit Name 'firstCar2' und Aliasen 'bobbyCar' und 'car2'
    @Bean({"firstCar2", "bobbyCar", "car2"})
    Car firstCar2() {
        return new Car();
    }
    //end::beans[]
    
    
    // Siehe https://www.logicbig.com/tutorials/spring-framework/spring-core/javaconfig-methods-inter-dependency.html
    // zum Umgang mit Mehrdeutigkeiten.
    @Bean
    Engine gasEngine() {
        return new Engine("gas", 32);
    }

    // tag::wiring[]
    // Ist Singleton, d. h. gibt es nur einmal!
    @Bean
    Engine engine() {
        return new Engine("diesel", 48);
    }

    @Bean
    Car realCar() {
        Car car = new Car();
        car.setColor("yellow");
        car.setEngine(engine()); // Verdrahtung über Methode (Cache!)
        return car;
    }

    @Bean
    Car realCar2(Engine engine) { // Verdrahtung über Parameter
        Car car = new Car();
        car.setEngine(engine);
        return car;
    }
    // end::wiring[]

    // tag::collections[]
    @Bean
    Feature aircon() {
        return new Feature("air conditioning");
    }
    
    @Bean
    Car luxuryCar() {
        Car car = new Car();
        car.setColor("black");
        car.setEngine(new Engine("gas", 120));
        car.setFeatures(List.of(aircon(), new Feature("radio")));
        return car;
    }
    // end::collections[]
// tag::beans[]
}
//end::beans[]