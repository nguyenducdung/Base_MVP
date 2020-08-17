package com.dungnd.basemvp.view.impl;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.FragmentHomeUserBinding;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.DaggerHomeuserviewComponent;
import com.dungnd.basemvp.injection.HomeuserviewModule;
import com.dungnd.basemvp.presenter.HomeUserPresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.view.HomeUserView;
import com.dungnd.basemvp.view.base.BaseFragment;

import javax.inject.Inject;

public final class HomeUserFragment extends BaseFragment<HomeUserPresenter, HomeUserView, FragmentHomeUserBinding> implements HomeUserView {
    @Inject
    PresenterFactory<HomeUserPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    public HomeUserFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHomeuserviewComponent.builder()
                .appComponent(parentComponent)
                .homeuserviewModule(new HomeuserviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_user;
    }

    @Override
    public boolean onBackPress() {
        return false;
    }

    @NonNull
    @Override
    protected PresenterFactory<HomeUserPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void initView() {

    }
}
