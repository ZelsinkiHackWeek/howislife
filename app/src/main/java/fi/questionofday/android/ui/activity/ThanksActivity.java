package fi.questionofday.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.TextView;

import butterknife.BindView;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.presenter.ThanksActivityPresenter;

public class ThanksActivity extends BaseActivity implements
        ThanksActivityPresenter.ThanksActivityView {
    private static final String ARG_EMOTICON = "emoticon";

    @BindView(R.id.a_thanks_selected) TextView textSelected;

    private ThanksActivityPresenter presenter;
    private String emoticon;

    public static void launch(Activity launchingActivity, @Nullable TextView v) {
        Intent intent = new Intent(launchingActivity, ThanksActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(launchingActivity, v, "a_thanks_selected");
        intent.putExtra(ARG_EMOTICON, v.getText().toString());
        launchingActivity.startActivity(intent, options.toBundle());
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
        if (savedInstanceState != null) {
            emoticon = savedInstanceState.getString(ARG_EMOTICON);
        }
        else {
            emoticon = getIntent().getStringExtra(ARG_EMOTICON);
        }
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.a_thanks;
    }

    private void initView() {
        textSelected.setText(emoticon);
    }

    @Override
    public void openStatistics() {
    }
}
