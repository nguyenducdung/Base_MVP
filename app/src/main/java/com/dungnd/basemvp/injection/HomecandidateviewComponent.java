package com.dungnd.basemvp.injection;

import com.dungnd.basemvp.view.impl.HomeCandidateFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HomecandidateviewModule.class)
public interface HomecandidateviewComponent {
    void inject(HomeCandidateFragment fragment);
}