package com.dungnd.basemvp.presenter.impl;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.presenter.HomeUserPresenter;
import com.dungnd.basemvp.view.HomeUserView;
import com.dungnd.basemvp.interactor.HomeUserInteractor;

import javax.inject.Inject;

public final class HomeUserPresenterImpl extends BasePresenterImpl<HomeUserView> implements HomeUserPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final HomeUserInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public HomeUserPresenterImpl(@NonNull HomeUserInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onStart(boolean viewCreated) {
        super.onStart(viewCreated);

        // Your code here. Your view is available using mView and will not be null until next onStop()
    }

    @Override
    public void onStop() {
        // Your code here, mView will be null after this method until next onStart()

        super.onStop();
    }

    @Override
    public void onPresenterDestroyed() {
        /*
         * Your code here. After this method, your presenter (and view) will be completely destroyed
         * so make sure to cancel any HTTP call or database connection
         */

        super.onPresenterDestroyed();
    }
}