package com.dungnd.basemvp.view.impl;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.FragmentHomeJobBinding;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.DaggerHomejobviewComponent;
import com.dungnd.basemvp.injection.HomejobviewModule;
import com.dungnd.basemvp.presenter.HomeJobPresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.view.HomeJobView;
import com.dungnd.basemvp.view.base.BaseFragment;

import javax.inject.Inject;

public final class HomeJobFragment extends BaseFragment<HomeJobPresenter, HomeJobView, FragmentHomeJobBinding> implements HomeJobView {
    @Inject
    PresenterFactory<HomeJobPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    public HomeJobFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHomejobviewComponent.builder()
                .appComponent(parentComponent)
                .homejobviewModule(new HomejobviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_job;
    }

    @Override
    public boolean onBackPress() {
        return false;
    }

    @NonNull
    @Override
    protected PresenterFactory<HomeJobPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void initView() {

    }
}
