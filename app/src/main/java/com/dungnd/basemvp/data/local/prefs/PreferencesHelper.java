package com.dungnd.basemvp.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dungnd.basemvp.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class PreferencesHelper implements RxPreferenceHelper {
    private static final String PREF_KEY_RECENT = "PREF_KEY_RECENT";

    private SharedPreferences mPrefs;
    private static PreferencesHelper sPreferencesHelper;

    public static PreferencesHelper getInstance(Context context) {
        if (sPreferencesHelper == null) {
            sPreferencesHelper = new PreferencesHelper(context);
        }
        return sPreferencesHelper;
    }

    @Inject
    PreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    @Override
    public String getString(String key) {
        return mPrefs.getString(key, "");
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key) {
        return mPrefs.getBoolean(key, false);
    }

    @Override
    public void writeMapRecentSearches(String searchRecent) {
        saveLinkedListString(searchRecent, 3);
    }

    @Override
    public List<String> getRecentMapSearch() {
        return getLinkedListString(PREF_KEY_RECENT);
    }

    private void saveLinkedListString(String value, int maxCount) {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_RECENT, "");
        LinkedList<String> searchList;
        if (!TextUtils.isEmpty(json)) {
            searchList = gson.fromJson(json, LinkedList.class);
            if (searchList.contains(value)) {
                searchList.remove(value);
            }
            if (searchList.size() == maxCount) {
                searchList.removeLast();
            }
            searchList.addFirst(value);
        } else {
            searchList = new LinkedList<>();
            searchList.addFirst(value);
        }
        String linkedListJson = gson.toJson(searchList);
        mPrefs.edit().putString(PREF_KEY_RECENT, linkedListJson).apply();
    }

    private List<String> getLinkedListString(String key) {
        Gson gson = new Gson();
        String json = mPrefs.getString(key, "");
        if (!TextUtils.isEmpty(json)) {
            LinkedList<String> searchList = gson.fromJson(json, LinkedList.class);
            return new ArrayList<>(searchList);
        } else {
            return new ArrayList<>();
        }
    }
}
