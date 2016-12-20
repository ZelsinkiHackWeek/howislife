package fi.questionofday.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;
import fi.questionofday.android.ui.presenter.BasePresenter;

public abstract class BaseActivity extends FragmentActivity implements BasePresenter.View {

    public abstract BasePresenter getPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(this);
        //noinspection unchecked
        getPresenter().setView(this);
        getPresenter().initialize();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().pause();
    }

    @Override
    protected void onDestroy() {
        getPresenter().destroy();
        super.onDestroy();
    }
}
