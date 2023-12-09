package prefs;

public class DI {

    interface UserProvider {}
    interface PreferencesRepository {}

    class PreferencesServiceImpl implements PreferencesService {

        private UserProvider userProvider;
        private PreferencesRepository preferencesRepository;

        @Override
        public void savePreference(String key, String value) {

        }

        @Override
        public String loadPreference(String key) {
            return null;
        }

        public UserProvider getUserProvider() {
            return userProvider;
        }

        public void setUserProvider(UserProvider userProvider) {
            this.userProvider = userProvider;
        }

        public PreferencesRepository getPreferencesRepository() {
            return preferencesRepository;
        }

        public void setPreferencesRepository(PreferencesRepository preferencesRepository) {
            this.preferencesRepository = preferencesRepository;
        }
    }

    class FxUserProvider implements UserProvider {
    }

    class JdbcPreferencesRepository implements PreferencesRepository {
    }

    // tag::plumb[]
    private FxUserProvider userProvider;
    private JdbcPreferencesRepository preferencesRepository;
    private PreferencesServiceImpl preferencesService;

    private void plumb() {
        userProvider = new FxUserProvider();
        preferencesRepository = new JdbcPreferencesRepository();
        preferencesService = new PreferencesServiceImpl();
        preferencesService.setUserProvider(userProvider);
        preferencesService.setPreferencesRepository(preferencesRepository);
    }
    // end::plumb[]
}
