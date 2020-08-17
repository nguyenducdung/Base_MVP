package com.dungnd.basemvp.injection;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.interactor.HomepageInteractor;
import com.dungnd.basemvp.interactor.impl.HomepageInteractorImpl;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.HomepagePresenter;
import com.dungnd.basemvp.presenter.impl.HomepagePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HomepageviewModule {
    @Provides
    public HomepageInteractor provideInteractor() {
        return new HomepageInteractorImpl();
    }

    @Provides
    public PresenterFactory<HomepagePresenter> providePresenterFactory(@NonNull final HomepageInteractor interactor) {
        return new PresenterFactory<HomepagePresenter>() {
            @NonNull
            @Override
            public HomepagePresenter create() {
                return new HomepagePresenterImpl(interactor);
            }
        };
    }
}
