package fi.howislife.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fi.howislife.android.R;
import fi.howislife.android.ui.presenter.BasePresenter;
import fi.howislife.android.ui.presenter.MainActivityPresenter;
import fi.howislife.android.ui.util.AnimationUtils;

public class MainActivity extends BaseActivity implements MainActivityPresenter.MainActivityView {

    @BindView(R.id.a_main_button_super_happy) TextView buttonSuperHappy;
    @BindView(R.id.a_main_button_happy) TextView buttonHappy;
    @BindView(R.id.a_main_button_meh) TextView buttonMeh;
    @BindView(R.id.a_main_button_sad) TextView buttonSad;

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
        return R.layout.a_main;
    }

    private void initView() {
        buttonSuperHappy.setText(new String(Character.toChars(0x1F601)));
        buttonHappy.setText(new String(Character.toChars(0x1F60A)));
        buttonMeh.setText(new String(Character.toChars(0x1F612)));
        buttonSad.setText(new String(Character.toChars(0x1F622)));
    }

    @Override
    public void openStatistics() {
    }

    @OnClick({R.id.a_main_button_super_happy,
            R.id.a_main_button_happy,
            R.id.a_main_button_meh,
            R.id.a_main_button_sad})
    public void onEmoticonClick(View view) {
        AnimationUtils.clickAnimation(view);
    }
}
