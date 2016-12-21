package fi.questionofday.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.List;

import butterknife.BindView;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.adapter.FeedbackAdapter;
import fi.questionofday.android.ui.presenter.StatisticsActivityPresenter;

public class StatisticsActivity extends BaseActivity implements
        StatisticsActivityPresenter.StatisticsActivityView {

    @BindView(R.id.a_statistics_pie_chart) PieChart pieChart;
    @BindView(R.id.a_statistics_recyclerview) RecyclerView recyclerView;

    private StatisticsActivityPresenter presenter;
    private FeedbackAdapter feedbackAdapter;

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
    public void showFeedback(List<Feedback> feedbackList) {
        if (feedbackAdapter == null) {
            feedbackAdapter = new FeedbackAdapter(new ArrayList<>(), presenter);
            recyclerView.setAdapter(feedbackAdapter);
        }
        feedbackAdapter.setItems(feedbackList);
    }

    @Override
    public void showFeedback(Feedback feedbackToShow) {
        final List<PieEntry> entries = new ArrayList<>();
        final List<Integer> colors = new ArrayList<>();
        if (feedbackToShow.getStar4() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar4(), "Super happy"));
            colors.add(Color.rgb(0, 181, 60));
        }
        if (feedbackToShow.getStar3() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar3(), "Happy"));
            colors.add(Color.rgb(0, 217, 137));
        }
        if (feedbackToShow.getStar2() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar2(), "Meh"));
            colors.add(Color.rgb(0, 217, 212));
        }
        if (feedbackToShow.getStar1() > 0) {
            entries.add(new PieEntry(feedbackToShow.getStar1(), "Sad"));
            colors.add(Color.rgb(56, 135, 214));
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
        final Legend l = pieChart.getLegend();
        l.setTextSize(16f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setXOffset(-60f);
        pieChart.invalidate();
    }
}
