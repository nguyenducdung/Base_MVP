package com.dungnd.basemvp.data.local.prefs;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {
    @Provides
    @Singleton
    public RxPreferenceHelper providePreferencesHelper(Context context) {
        return PreferencesHelper.getInstance(context);
    }
}
