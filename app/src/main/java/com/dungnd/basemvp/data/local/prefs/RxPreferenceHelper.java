package com.dungnd.basemvp.data.local.prefs;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public interface RxPreferenceHelper {

    void saveString(String key, String value);

    String getString(String key);

    void saveBoolean(String key, boolean value);

    boolean getBoolean(String key);

    void writeMapRecentSearches(String searchRecent);

    List<String> getRecentMapSearch();
}
