package fi.howislife.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import fi.howislife.android.R;
import fi.howislife.android.data.QuestionRepository;
import fi.howislife.android.domain.QuestionService;
import fi.howislife.android.helper.SubscriptionHelper;
import fi.howislife.android.ui.presenter.ThanksActivityPresenter;

public class ThanksActivity extends BaseActivity implements
        ThanksActivityPresenter.ThanksActivityView {

    private ThanksActivityPresenter presenter;

    public static void launch(Activity launchingActivity) {
        Intent intent = new Intent(launchingActivity, ThanksActivity.class);
        launchingActivity.startActivity(intent);
    }

    @Override
    public ThanksActivityPresenter getPresenter() {
        // Add here all dependencies to the presenter
        if (presenter == null) {
            presenter = new ThanksActivityPresenter(new SubscriptionHelper(),
                    new QuestionService(QuestionRepository.getInstance()));
        }
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.a_thanks;
    }

    private void initView() {
    }

    @Override
    public void openStatistics() {
    }
}
