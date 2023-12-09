package prefs;

interface UserProvider {
    String getUserId();
}

interface PreferencesRepository {
    String loadPreference(String userId, String key);
}

interface ServiceRegistry {
    UserProvider getUserProvider();

    PreferencesRepository getPreferencesRepository();
}

abstract class ServiceRegistryFactory {
    public static ServiceRegistryFactory newInstance() {
        String factoryName = System.getProperty("serviceRegistryFactory", "lesson01.FxServiceRegistryFactory");
        try {
            return (ServiceRegistryFactory) Class.forName(factoryName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            throw new IllegalStateException("Unable to create " + factoryName, ex);
        }
    }

    public abstract ServiceRegistry getServiceRegistry();
}

class PreferencesServiceImpl implements PreferencesService {
    // tag::ServiceRegistry[]
    ServiceRegistry registry = ServiceRegistryFactory
            .newInstance().getServiceRegistry();

    @Override
    public String loadPreference(String key) {
        String userId = registry
                .getUserProvider()
                .getUserId();
        return registry
                .getPreferencesRepository()
                .loadPreference(userId, key);
    }
    // end::ServiceRegistry[]

    @Override
    public void savePreference(String key, String value) {
    }
}
