package com.dungnd.basemvp.view.impl;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.FragmentHomepageBinding;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.DaggerHomepageviewComponent;
import com.dungnd.basemvp.injection.HomepageviewModule;
import com.dungnd.basemvp.presenter.HomepagePresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.view.HomepageView;
import com.dungnd.basemvp.view.base.BaseFragment;

import javax.inject.Inject;

public final class HomepageFragment extends BaseFragment<HomepagePresenter, HomepageView, FragmentHomepageBinding> implements HomepageView {
    @Inject
    PresenterFactory<HomepagePresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    public HomepageFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHomepageviewComponent.builder()
                .appComponent(parentComponent)
                .homepageviewModule(new HomepageviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_homepage;
    }

    @Override
    public boolean onBackPress() {
        return false;
    }

    @NonNull
    @Override
    protected PresenterFactory<HomepagePresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void initView() {

    }
}
