package com.dungnd.basemvp.view.impl;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.FragmentHomeBinding;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.DaggerHomeviewComponent;
import com.dungnd.basemvp.injection.HomeviewModule;
import com.dungnd.basemvp.presenter.HomePresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.view.HomeView;
import com.dungnd.basemvp.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class HomeFragment extends BaseFragment<HomePresenter, HomeView, FragmentHomeBinding> implements HomeView {
    @Inject
    PresenterFactory<HomePresenter> mPresenterFactory;
    public static final int POSITION_TAB_HOME = 0;
    public static final int POSITION_TAB_JOB = 1;
    public static final int POSITION_TAB_CANDIDATE = 2;
    public static final int POSITION_TAB_USER = 3;
    private HomepageFragment homepageFragment = new HomepageFragment();
    private HomeJobFragment homeJobFragment = new HomeJobFragment();
    private HomeCandidateFragment homeCandidateFragment = new HomeCandidateFragment();
    private HomeUserFragment homeUserFragment = new HomeUserFragment();
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private int currentPosition = 0;

    // Your presenter is available using the mPresenter variable

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHomeviewComponent.builder()
                .appComponent(parentComponent)
                .homeviewModule(new HomeviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @NonNull
    @Override
    protected PresenterFactory<HomePresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void initView() {
        initListTab();
        binding.bnvHome.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    openTab(POSITION_TAB_HOME);
                    return true;
                case R.id.navigation_gifts:
                    openTab(POSITION_TAB_JOB);
                    return true;
                case R.id.navigation_cart:
                    openTab(POSITION_TAB_CANDIDATE);
                    return true;
                case R.id.navigation_profile:
                    openTab(POSITION_TAB_USER);
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onBackPress() {
        if (currentPosition == 0) {
            return false;
        } else {
            binding.bnvHome.setSelectedItemId(R.id.navigation_shop);
            openTab(POSITION_TAB_HOME);
            return true;
        }
    }

    private void openTab(int position) {
        currentPosition = position;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            BaseFragment fragment = fragmentList.get(i);
            if (i == position) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_up);
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_down);
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    private void initListTab() {
        fragmentList.add(POSITION_TAB_HOME, homepageFragment);
        fragmentList.add(POSITION_TAB_JOB, homeJobFragment);
        fragmentList.add(POSITION_TAB_CANDIDATE, homeCandidateFragment);
        fragmentList.add(POSITION_TAB_USER, homeUserFragment);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            fragmentTransaction.add(R.id.fr_container, fragment, fragment.getClass().getSimpleName());
            if (i != 0) {
                fragmentTransaction.hide(fragment);
            } else {
                fragmentTransaction.show(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
