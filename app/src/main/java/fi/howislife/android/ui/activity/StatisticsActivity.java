package fi.howislife.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import fi.howislife.android.R;
import fi.howislife.android.data.QuestionRepository;
import fi.howislife.android.domain.QuestionService;
import fi.howislife.android.helper.SubscriptionHelper;
import fi.howislife.android.ui.presenter.StatisticsActivityPresenter;

public class StatisticsActivity extends BaseActivity implements
        StatisticsActivityPresenter.StatisticsActivityView {

    private StatisticsActivityPresenter presenter;

    public static void launch(Activity launchingActivity) {
        Intent intent = new Intent(launchingActivity, StatisticsActivity.class);
        launchingActivity.startActivity(intent);
    }

    @Override
    public StatisticsActivityPresenter getPresenter() {
        // Add here all dependencies to the presenter
        if (presenter == null) {
            presenter = new StatisticsActivityPresenter(new SubscriptionHelper(),
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
        return R.layout.a_statistics;
    }

    private void initView() {
    }

    @Override
    public void openStatistics() {
    }
}
