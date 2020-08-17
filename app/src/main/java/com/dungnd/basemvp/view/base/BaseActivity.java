package com.dungnd.basemvp.view.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.dungnd.basemvp.MainApplication;
import com.dungnd.basemvp.R;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.presenter.BasePresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.loader.PresenterLoader;
import com.dungnd.basemvp.utils.Constants;
import com.dungnd.basemvp.view.BaseView;
import com.dungnd.basemvp.view.dialog.DialogConfirm;
import com.dungnd.basemvp.view.dialog.DialogLoading;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView, T extends ViewDataBinding> extends AppCompatActivity implements LoaderManager.LoaderCallbacks<P>, BaseView {
    /**
     * Do we need to call {@link #doStart()} from the {@link #onLoadFinished(Loader, BasePresenter)} method.
     * Will be true if presenter wasn't loaded when {@link #onStart()} is reached
     */
    private final AtomicBoolean mNeedToCallStart = new AtomicBoolean(false);

    protected T binding;

    /**
     * The presenter for this view
     */
    @Nullable
    protected P mPresenter;
    /**
     * Is this the first start of the activity (after onCreate)
     */
    private boolean mFirstStart;

    private CompositeDisposable mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirstStart = true;

        binding = DataBindingUtil.setContentView(this, getLayoutResId());

        injectDependencies();

        getSupportLoaderManager().initLoader(0, null, this).startLoading();
    }

    private void injectDependencies() {
        setupComponent(((MainApplication) getApplication()).getAppComponent());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mPresenter == null) {
            mNeedToCallStart.set(true);
        } else {
            doStart();
        }
    }

    /**
     * Call the presenter callbacks for onStart
     */
    @SuppressWarnings("unchecked")
    private void doStart() {
        assert mPresenter != null;

        mPresenter.onViewAttached((V) this);

        mPresenter.onStart(mFirstStart);

        mFirstStart = false;
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();

            mPresenter.onViewDetached();
        }

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCompositeSubscription = new CompositeDisposable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompositeSubscription.dispose();
    }

    @Override
    public final Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, getPresenterFactory());
    }

    @Override
    public final void onLoadFinished(Loader<P> loader, P presenter) {
        mPresenter = presenter;

        if (mNeedToCallStart.compareAndSet(true, false)) {
            doStart();
        }
    }

    @Override
    public final void onLoaderReset(Loader<P> loader) {
        mPresenter = null;
    }

    /**
     * Get the presenter factory implementation for this view
     *
     * @return the presenter factory
     */
    @NonNull
    protected abstract PresenterFactory<P> getPresenterFactory();

    /**
     * Setup the injection component for this view
     *
     * @param appComponent the app component
     */
    protected abstract void setupComponent(@NonNull AppComponent appComponent);

    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * Avoid duplicate click listener at the same Time
     *
     * @return True if occurred duplicate click, else other wise
     */
    protected boolean avoidDuplicateClick() {
        long now = System.currentTimeMillis();
        if (now - BaseFragment.lastClickTime < Constants.CLICK_TIME_INTERVAL) {
            return true;
        }
        BaseFragment.lastClickTime = now;
        return false;
    }

    @Override
    public void showDialog(String messageRes) {
        new DialogConfirm(this, null, messageRes,
                getString(R.string.ok), null,
                null, null, true).showDialog();
    }

    @Override
    public void showLoading() {
        DialogLoading.getInstance(this).show();
    }

    @Override
    public void hiddenLoading() {
        DialogLoading.getInstance(this).hidden();
    }

    @Override
    public void showDialog(String title, String message) {
        new DialogConfirm(this, title, message,
                getString(R.string.ok), null,
                null, null, false).showDialog();
    }

    @Override
    protected void onDestroy() {
        DialogLoading.getInstance(this).destroyLoadingDialog();
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void initView() {

    }
}
