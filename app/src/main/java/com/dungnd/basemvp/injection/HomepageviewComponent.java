package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.HomepageFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HomepageviewModule.class)
public interface HomepageviewComponent {
    void inject(HomepageFragment fragment);
}