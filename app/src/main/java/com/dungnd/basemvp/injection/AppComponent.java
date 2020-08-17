package com.dungnd.basemvp.injection;

import android.content.Context;

import com.dungnd.basemvp.MainApplication;
import com.dungnd.basemvp.data.local.prefs.RxPreferenceHelper;
import com.dungnd.basemvp.data.local.prefs.SharedPreferenceModule;
import com.dungnd.basemvp.data.local.realm.RealmManager;
import com.dungnd.basemvp.data.local.realm.RealmModule;
import com.dungnd.basemvp.network.ApiModule;
import com.dungnd.basemvp.network.Apis;
import com.dungnd.basemvp.utils.rx.RxSchedulers;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, RxModule.class, SharedPreferenceModule.class, RealmModule.class})
public interface AppComponent {

    Context getAppContext();

    MainApplication getApp();

    Apis appApis();

    RxSchedulers rxSchedulers();

    RxPreferenceHelper getPreference();

    RealmManager realmManager();
}