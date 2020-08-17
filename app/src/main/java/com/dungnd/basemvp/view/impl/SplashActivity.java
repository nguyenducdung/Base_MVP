package com.dungnd.basemvp.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.dungnd.basemvp.R;
import com.dungnd.basemvp.databinding.ActivitySplashBinding;
import com.dungnd.basemvp.view.SplashView;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.SplashPresenter;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.injection.SplashviewModule;
import com.dungnd.basemvp.injection.DaggerSplashviewComponent;
import com.dungnd.basemvp.view.base.BaseActivity;

import javax.inject.Inject;

public final class SplashActivity extends BaseActivity<SplashPresenter, SplashView, ActivitySplashBinding> implements SplashView {
    @Inject
    PresenterFactory<SplashPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerSplashviewComponent.builder()
                .appComponent(parentComponent)
                .splashviewModule(new SplashviewModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @NonNull
    @Override
    protected PresenterFactory<SplashPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void initView() {
        super.initView();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
