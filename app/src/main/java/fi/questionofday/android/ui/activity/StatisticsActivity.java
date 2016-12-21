package fi.questionofday.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.adapter.QuestionAdapter;
import fi.questionofday.android.ui.presenter.StatisticsActivityPresenter;

public class StatisticsActivity extends BaseActivity implements
        StatisticsActivityPresenter.StatisticsActivityView {

    @BindView(R.id.a_statistics_pie_chart) PieChart pieChart;
    @BindView(R.id.a_statistics_recyclerview) RecyclerView recyclerView;

    private StatisticsActivityPresenter presenter;
    private QuestionAdapter questionAdapter;

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
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_general,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showQuestions(List<Question> questionList) {
        if (questionAdapter == null) {
            questionAdapter = new QuestionAdapter(new ArrayList<>(), presenter);
            recyclerView.setAdapter(questionAdapter);
            if (questionList.size() > 0) {
                getPresenter().onQuestionClicked(questionList.get(0));
            }
        }
        questionAdapter.setItems(questionList);
    }

    boolean once = false;
    @Override
    public void showFeedback(Feedback feedbackToShow) {

        final List<PieEntry> entries = new ArrayList<>();
        final List<Integer> colors = new ArrayList<>();
        if (feedbackToShow.getStar4() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar4(), "Super happy"));
            colors.add(ContextCompat.getColor(this, R.color.super_happy));
        }
        if (feedbackToShow.getStar3() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar3(), "Happy"));
            colors.add(ContextCompat.getColor(this, R.color.happy));
        }
        if (feedbackToShow.getStar2() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar2(), "Meh"));
            colors.add(ContextCompat.getColor(this, R.color.meh));
        }
        if (feedbackToShow.getStar1() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar1(), "Sad"));
            colors.add(ContextCompat.getColor(this, R.color.sad));
        }
        final PieDataSet pieDataSet = new PieDataSet(entries, null);
        pieDataSet.setValueTextSize(20f);
        pieDataSet.setColors(colors);
        final PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(16f);
        pieChart.setData(pieData);
        final Description description = new Description();
        description.setTextSize(16f);
        description.setText("Total votes: " + feedbackToShow.getTotal());
        pieChart.setDescription(description);
        pieChart.setCenterText(feedbackToShow.getQuestion().getText());
        pieChart.setCenterTextSize(20f);
        pieChart.setCenterTextRadiusPercent(90f);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(16f);

        //Init entries:
        final Legend l = pieChart.getLegend();
        l.setEntries(Collections.emptyList());
        pieChart.invalidate();
    }
}
