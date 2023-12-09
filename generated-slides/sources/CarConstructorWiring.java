package container.fragments.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import container.fragments.Engine;
import container.fragments.Feature;

@Component
public class CarConstructorWiring {
    // tag::x[]
    private final Engine engine;
    private final List<Feature> features;

    @Autowired
    public CarConstructorWiring(Engine engine, List<Feature> features) {
        this.engine = engine;
        this.features = features;
    }
    // end::x[]
    
    public String toString() {
        return engine + ", features: " + features;
    }
}
