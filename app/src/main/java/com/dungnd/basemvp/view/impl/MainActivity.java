package com.dungnd.basemvp.view.impl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.ActivityMainBinding;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.DaggerMainviewComponent;
import com.dungnd.basemvp.injection.MainviewModule;
import com.dungnd.basemvp.presenter.MainPresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.utils.DeviceUtil;
import com.dungnd.basemvp.view.MainView;
import com.dungnd.basemvp.view.base.BaseActivity;
import com.dungnd.basemvp.view.base.BaseFragment;
import com.dungnd.basemvp.view.dialog.DialogConfirm;

import javax.inject.Inject;

public final class MainActivity extends BaseActivity<MainPresenter, MainView, ActivityMainBinding> implements MainView {
    @Inject
    PresenterFactory<MainPresenter> mPresenterFactory;
    private NavController navController;
    private NavHostFragment navHostFragment;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerMainviewComponent.builder()
                .appComponent(parentComponent)
                .mainviewModule(new MainviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected PresenterFactory<MainPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment instanceof BaseFragment) {
            if (((BaseFragment) fragment).onBackPress()) {
                return;
            }
        }
        int countFragment = navHostFragment.getChildFragmentManager().getBackStackEntryCount();
        DeviceUtil.hideSoftKeyboard(this);
        if (navController.getCurrentDestination() != null) {
            if (countFragment <= 1) {
                showDialogExitApp();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void showDialogExitApp() {
        new DialogConfirm(this, getString(R.string.dialog_title), getString(R.string.dialog_finish_app), getString(R.string.dialog_accept), v -> finish(), getString(R.string.dialog_close), null, true)
                .showDialog();
    }

    @Override
    public void initView() {
    }
}
