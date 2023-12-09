package container.fragments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CarFieldWiring {
    // tag::x[]
    @Autowired
    private Engine engine;
    @Autowired
    private List<Feature> features;
    // end::x[]

    public String toString() {
        return engine + ", features: " + features;
    }
}
