package fi.howislife.android.ui.activity;

import android.os.Bundle;

import com.karumi.rosie.view.Presenter;
import com.karumi.rosie.view.RosieActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import fi.howislife.android.R;
import fi.howislife.android.module.MainModule;
import fi.howislife.android.ui.presenter.MainActivityPresenter;

public class MainActivity extends RosieActivity {

    @Inject @Presenter MainActivityPresenter mainActivityPresenter;

    @Override
    protected List<Object> getActivityScopeModules() {
        return Arrays.asList((Object) new MainModule());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
