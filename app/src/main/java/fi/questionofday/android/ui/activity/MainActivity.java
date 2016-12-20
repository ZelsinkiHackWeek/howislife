package fi.questionofday.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.presenter.MainActivityPresenter;
import fi.questionofday.android.ui.util.AnimationUtils;

public class MainActivity extends BaseActivity implements MainActivityPresenter.MainActivityView {

    private MainActivityPresenter presenter;
    final Random random = new Random();
    final int minDelay = 150;
    final int minDuration = 100;

    @BindView(R.id.a_main_question_of_the_day) TextView textQuestion;
    @BindView(R.id.a_main_button_super_happy) ImageView buttonSuperHappy;
    @BindView(R.id.a_main_button_happy) ImageView buttonHappy;
    @BindView(R.id.a_main_button_meh) ImageView buttonMeh;
    @BindView(R.id.a_main_button_sad) ImageView buttonSad;

    private Question question;

    @Override
    public MainActivityPresenter getPresenter() {
        // Add here all dependencies to the presenter
        if (presenter == null) {
            presenter = new MainActivityPresenter(new SubscriptionHelper(),
                    new QuestionService(
                            QuestionRepository.getInstance()
                    ));
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
        return R.layout.a_main;
    }

    private void initView() {
        buttonSuperHappy.setTag(R.drawable.emoji_super_happy);
        buttonHappy.setTag(R.drawable.emoji_happy);
        buttonMeh.setTag(R.drawable.emoji_meh);
        buttonSad.setTag(R.drawable.emoji_sad);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Delay until ready to animate:
        buttonSuperHappy.post(() -> {
            animateView(buttonSuperHappy);
            animateView(buttonHappy);
            animateView(buttonMeh);
            animateView(buttonSad);
        });
    }

    private void animateView(View view) {
        //Reset values:
        view.setScaleX(0);
        view.setScaleY(0);
        //Animate:
        view.animate()
                .scaleY(1f)
                .scaleX(1f)
                .setDuration(random.nextInt(100)+minDuration)
                .setStartDelay(random.nextInt(100)+minDelay)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @Override
    public void openStatistics() {
        StatisticsActivity.launch(this);
    }

    @Override
    public void showQuestion(Question question) {
        this.question = question;
        textQuestion.setText(question.getText());
    }

    @Override
    public void showError() {
        Toast.makeText(this, "All the errors in the world happened all at once",
                Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.a_main_button_statistics})
    public void onStatisticsClick(View view) {
        openStatistics();
    }

    @OnClick({R.id.a_main_button_super_happy,
            R.id.a_main_button_happy,
            R.id.a_main_button_meh,
            R.id.a_main_button_sad})
    public void onEmoticonClick(ImageView view) {
        AnimationUtils.clickAnimation(view, () -> {
            getPresenter().submitResult();
            ThanksActivity.launch(this, view);
        });
    }
}
