package fi.questionofday.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.presenter.StatisticsActivityPresenter;

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
