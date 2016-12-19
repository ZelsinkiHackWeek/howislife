package fi.howislife.android.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import fi.howislife.android.R;
import fi.howislife.android.ui.presenter.BasePresenter;
import fi.howislife.android.ui.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivity implements MainActivityPresenter.MainActivityView {

    @BindView(R.id.button_happy) TextView happy;
    @BindView(R.id.button_sad) TextView sad;

    @Override
    public BasePresenter getPresenter() {
        // Add here all dependencies to the presenter
        return new MainActivityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        happy.setText(new String(Character.toChars(0x1F601)));
        sad.setText(new String(Character.toChars(0x1F61E)));
    }

    @Override
    public void showSomething() {
    }
}
