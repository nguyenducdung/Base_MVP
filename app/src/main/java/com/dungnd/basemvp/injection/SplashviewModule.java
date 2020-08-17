package com.dungnd.basemvp.injection;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.interactor.SplashInteractor;
import com.dungnd.basemvp.interactor.impl.SplashInteractorImpl;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.SplashPresenter;
import com.dungnd.basemvp.presenter.impl.SplashPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class SplashviewModule {
    @Provides
    public SplashInteractor provideInteractor() {
        return new SplashInteractorImpl();
    }

    @Provides
    public PresenterFactory<SplashPresenter> providePresenterFactory(@NonNull final SplashInteractor interactor) {
        return new PresenterFactory<SplashPresenter>() {
            @NonNull
            @Override
            public SplashPresenter create() {
                return new SplashPresenterImpl(interactor);
            }
        };
    }
}
