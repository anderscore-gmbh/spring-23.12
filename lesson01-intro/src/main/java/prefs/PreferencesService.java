package prefs;

// tag::PreferencesService[]
public interface PreferencesService {
    void savePreference(String key, String value);
    String loadPreference(String key);
}
// end::PreferencesService[]
