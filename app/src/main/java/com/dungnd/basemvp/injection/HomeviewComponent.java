package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HomeviewModule.class)
public interface HomeviewComponent {
    void inject(HomeFragment fragment);
}