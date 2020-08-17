package com.dungnd.basemvp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.deploygate.sdk.DeployGate;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.AppModule;
import com.dungnd.basemvp.injection.DaggerAppComponent;
import com.dungnd.basemvp.utils.Constants;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    private AppComponent mAppComponent;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        context = mAppComponent.getAppContext();

        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Constants.REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        // Deploy tool
        DeployGate.install(this);
    }

    @NonNull
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getContext() {
        return context;
    }
}
