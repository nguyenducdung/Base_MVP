package com.dungnd.basemvp.injection;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.interactor.HomeUserInteractor;
import com.dungnd.basemvp.interactor.impl.HomeUserInteractorImpl;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.HomeUserPresenter;
import com.dungnd.basemvp.presenter.impl.HomeUserPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HomeuserviewModule {
    @Provides
    public HomeUserInteractor provideInteractor() {
        return new HomeUserInteractorImpl();
    }

    @Provides
    public PresenterFactory<HomeUserPresenter> providePresenterFactory(@NonNull final HomeUserInteractor interactor) {
        return new PresenterFactory<HomeUserPresenter>() {
            @NonNull
            @Override
            public HomeUserPresenter create() {
                return new HomeUserPresenterImpl(interactor);
            }
        };
    }
}
