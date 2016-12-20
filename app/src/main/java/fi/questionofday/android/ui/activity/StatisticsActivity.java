package fi.questionofday.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import butterknife.BindView;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.presenter.StatisticsActivityPresenter;

public class StatisticsActivity extends BaseActivity implements
        StatisticsActivityPresenter.StatisticsActivityView {

    @BindView(R.id.a_statistics_pie_chart) PieChart pieChart;
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
    public void showError() {
        Toast.makeText(this, "All the errors in the world happened all at once",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFeedback(List<Feedback> feedbackList) {
        Log.i(getClass().getSimpleName(), feedbackList.toString());
    }
}
