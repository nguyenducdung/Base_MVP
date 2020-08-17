package com.dungnd.basemvp.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.dungnd.basemvp.MainApplication;
import com.dungnd.basemvp.R;
import com.dungnd.basemvp.injection.AppComponent;
import com.dungnd.basemvp.presenter.BasePresenter;
import com.dungnd.basemvp.presenter.loader.PresenterFactory;
import com.dungnd.basemvp.presenter.loader.PresenterLoader;
import com.dungnd.basemvp.utils.Constants;
import com.dungnd.basemvp.utils.DeviceUtil;
import com.dungnd.basemvp.view.BaseView;
import com.dungnd.basemvp.view.dialog.DialogConfirm;
import com.dungnd.basemvp.view.dialog.DialogLoading;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseFragment<P extends BasePresenter<V>, V extends BaseView, T extends ViewDataBinding> extends Fragment implements LoaderManager.LoaderCallbacks<P>, BaseView {
    /**
     * Do we need to call {@link #doStart()} from the {@link #onLoadFinished(Loader, BasePresenter)} method.
     * Will be true if presenter wasn't loaded when {@link #onStart()} is reached
     */
    private final AtomicBoolean mNeedToCallStart = new AtomicBoolean(false);
    public static long lastClickTime = System.currentTimeMillis();
    /**
     * The presenter for this view
     */
    @Nullable
    protected P mPresenter;

    protected T binding;
    /**
     * Is this the first start of the fragment (after onCreate)
     */
    private boolean mFirstStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirstStart = true;

        injectDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this).startLoading();
    }

    public void hideKeyboard() {
        if (getActivity() != null) {
            DeviceUtil.hideSoftKeyboard(getActivity());
        }
    }

    private void injectDependencies() {
        setupComponent(((MainApplication) getActivity().getApplication()).getAppComponent());
    }

    @Override
    public void onStart() {
        super.onStart();
        lastClickTime = System.currentTimeMillis();
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
    public void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();

            mPresenter.onViewDetached();
        }

        super.onStop();
    }

    @Override
    public final Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getActivity(), getPresenterFactory());
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

    //endregion
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * This func has call when pressed back button on device.
     *
     * @return True if need destroy Activity
     */
    public abstract boolean onBackPress();

    /**
     * Avoid duplicate click listener at the same Time
     *
     * @return True if occurred duplicate click, else other wise
     */
    protected boolean avoidDuplicateClick() {
        long now = System.currentTimeMillis();
        if (now - lastClickTime < Constants.CLICK_TIME_INTERVAL) {
            return true;
        }
        lastClickTime = now;
        return false;
    }

    @Override
    public void showDialog(String messageRes) {
        new DialogConfirm(getContext(), null, messageRes,
                getString(R.string.ok), null,
                null, null, true).showDialog();
    }

    @Override
    public void showLoading() {
        DialogLoading.getInstance(getContext()).show();
    }

    @Override
    public void hiddenLoading() {
        DialogLoading.getInstance(getContext()).hidden();
    }

    @Override
    public void showDialog(String title, String message) {
        new DialogConfirm(getContext(), title, message,
                getString(R.string.ok), null,
                null, null, false).showDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogLoading.getInstance(getContext()).destroyLoadingDialog();
        if (binding != null) {
            binding.unbind();
        }
    }

}
