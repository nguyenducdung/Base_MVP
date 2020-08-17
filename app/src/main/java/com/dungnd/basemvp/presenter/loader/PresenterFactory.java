package com.dungnd.basemvp.presenter.loader;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.presenter.BasePresenter;

/**
 * Factory to implement to create a presenter
 */
public interface PresenterFactory<T extends BasePresenter> {
    @NonNull
    T create();
}
