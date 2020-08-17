package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.HomeUserFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HomeuserviewModule.class)
public interface HomeuserviewComponent {
    void inject(HomeUserFragment fragment);
}