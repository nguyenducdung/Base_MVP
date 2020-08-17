package com.dungnd.basemvp.injection;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.interactor.HomeJobInteractor;
import com.dungnd.basemvp.interactor.impl.HomeJobInteractorImpl;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.HomeJobPresenter;
import com.dungnd.basemvp.presenter.impl.HomeJobPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HomejobviewModule {
    @Provides
    public HomeJobInteractor provideInteractor() {
        return new HomeJobInteractorImpl();
    }

    @Provides
    public PresenterFactory<HomeJobPresenter> providePresenterFactory(@NonNull final HomeJobInteractor interactor) {
        return new PresenterFactory<HomeJobPresenter>() {
            @NonNull
            @Override
            public HomeJobPresenter create() {
                return new HomeJobPresenterImpl(interactor);
            }
        };
    }
}
