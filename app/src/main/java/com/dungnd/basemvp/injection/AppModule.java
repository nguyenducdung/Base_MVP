package com.dungnd.basemvp.injection;

import android.content.Context;

import com.dungnd.basemvp.MainApplication;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public final class AppModule {
    @NonNull
    private final MainApplication mApp;

    public AppModule(@NonNull MainApplication app) {
        mApp = app;
    }

    @Provides
    public Context provideAppContext() {
        return mApp;
    }

    @Provides
    public MainApplication provideApp() {
        return mApp;
    }
}
