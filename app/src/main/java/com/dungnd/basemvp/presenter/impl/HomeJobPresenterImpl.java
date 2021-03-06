package com.dungnd.basemvp.presenter.impl;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.presenter.HomeJobPresenter;
import com.dungnd.basemvp.view.HomeJobView;
import com.dungnd.basemvp.interactor.HomeJobInteractor;

import javax.inject.Inject;

public final class HomeJobPresenterImpl extends BasePresenterImpl<HomeJobView> implements HomeJobPresenter {
    /**
     * The interactor
     */
    @NonNull
    private final HomeJobInteractor mInteractor;

    // The view is available using the mView variable

    @Inject
    public HomeJobPresenterImpl(@NonNull HomeJobInteractor interactor) {
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