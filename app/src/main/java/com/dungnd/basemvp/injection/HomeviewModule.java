package com.dungnd.basemvp.injection;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.interactor.HomeInteractor;
import com.dungnd.basemvp.interactor.impl.HomeInteractorImpl;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.HomePresenter;
import com.dungnd.basemvp.presenter.impl.HomePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HomeviewModule {
    @Provides
    public HomeInteractor provideInteractor() {
        return new HomeInteractorImpl();
    }

    @Provides
    public PresenterFactory<HomePresenter> providePresenterFactory(@NonNull final HomeInteractor interactor) {
        return new PresenterFactory<HomePresenter>() {
            @NonNull
            @Override
            public HomePresenter create() {
                return new HomePresenterImpl(interactor);
            }
        };
    }
}
