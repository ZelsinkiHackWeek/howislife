package fi.questionofday.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.presenter.ThanksActivityPresenter;
import fi.questionofday.android.ui.util.SimpleAnimationListener;

public class ThanksActivity extends BaseActivity implements
        ThanksActivityPresenter.ThanksActivityView {
    private static final String ARG_EMOTICON = "emoticon";

    @BindView(R.id.a_thanks_selected) ImageView imageSelected;
    @BindView(R.id.a_thanks_zalando_logo) ImageView zalandoLogo;

    private ThanksActivityPresenter presenter;
    private Integer emoticon;

    public static void launch(Activity launchingActivity, ImageView logo, @Nullable ImageView v) {
        Intent intent = new Intent(launchingActivity, ThanksActivity.class);
        //noinspection unchecked
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(launchingActivity,
                        new Pair<>(v, "selected"),
                        new Pair<>(logo, "logo"));
        if (v != null) {
            intent.putExtra(ARG_EMOTICON, (Integer)v.getTag());
        }
        launchingActivity.startActivity(intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {
        //No back allowed
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
            emoticon = savedInstanceState.getInt(ARG_EMOTICON);
        }
        else {
            emoticon = getIntent().getIntExtra(ARG_EMOTICON, 0);
        }
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.a_thanks;
    }

    private void initView() {
        imageSelected.setImageResource(emoticon);
        imageSelected.post(this::runCloseAnimation);
    }

    @Override
    public void openStatistics() {
    }

    private void runCloseAnimation() {
        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(1f, 0.1f));
        animationSet.setFillAfter(true);
        animationSet.setDuration(2500);
        animationSet.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }
        });

        imageSelected.startAnimation(animationSet);
    }
}
