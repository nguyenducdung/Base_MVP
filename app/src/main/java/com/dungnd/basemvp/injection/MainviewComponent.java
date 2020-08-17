package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainviewModule.class)
public interface MainviewComponent {
    void inject(MainActivity activity);
}