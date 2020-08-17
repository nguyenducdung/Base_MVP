package com.dungnd.basemvp.injection;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.interactor.HomeCandidateInteractor;
import com.dungnd.basemvp.interactor.impl.HomeCandidateInteractorImpl;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.HomeCandidatePresenter;
import com.dungnd.basemvp.presenter.impl.HomeCandidatePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public final class HomecandidateviewModule {
    @Provides
    public HomeCandidateInteractor provideInteractor() {
        return new HomeCandidateInteractorImpl();
    }

    @Provides
    public PresenterFactory<HomeCandidatePresenter> providePresenterFactory(@NonNull final HomeCandidateInteractor interactor) {
        return new PresenterFactory<HomeCandidatePresenter>() {
            @NonNull
            @Override
            public HomeCandidatePresenter create() {
                return new HomeCandidatePresenterImpl(interactor);
            }
        };
    }
}
