package com.dungnd.basemvp.view.impl;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.FragmentHomeCandidateBinding;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.DaggerHomecandidateviewComponent;
import com.dungnd.basemvp.injection.HomecandidateviewModule;
import com.dungnd.basemvp.presenter.HomeCandidatePresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.view.HomeCandidateView;
import com.dungnd.basemvp.view.base.BaseFragment;

import javax.inject.Inject;

public final class HomeCandidateFragment extends BaseFragment<HomeCandidatePresenter, HomeCandidateView, FragmentHomeCandidateBinding> implements HomeCandidateView {
    @Inject
    PresenterFactory<HomeCandidatePresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    public HomeCandidateFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHomecandidateviewComponent.builder()
                .appComponent(parentComponent)
                .homecandidateviewModule(new HomecandidateviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_candidate;
    }

    @Override
    public boolean onBackPress() {
        return false;
    }

    @NonNull
    @Override
    protected PresenterFactory<HomeCandidatePresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void initView() {

    }
}
