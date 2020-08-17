package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.HomeJobFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HomejobviewModule.class)
public interface HomejobviewComponent {
    void inject(HomeJobFragment fragment);
}