package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = SplashviewModule.class)
public interface SplashviewComponent {
    void inject(SplashActivity activity);
}