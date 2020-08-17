package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.utils.rx.AppRxSchedulers;
import com.dungnd.basemvp.utils.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @Provides
    RxSchedulers provideRxSchedulers() {
        return new AppRxSchedulers();
    }
}
