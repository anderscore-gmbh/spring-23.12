package container.fragments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CarSetterWiring {
    private Engine engine;
    private List<Feature> features;

    public String toString() {
        return engine + ", features: " + features;
    }

    public Engine getEngine() {
        return engine;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    // tag::x[]
    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Autowired
    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    // end::x[]
}
