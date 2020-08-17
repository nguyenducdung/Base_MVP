package com.dungnd.basemvp.data.local.realm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RealmModule {

    @Provides
    @Singleton
    RealmManager provideRealmManager() {
        return RealmManager.getInstance();
    }
}
