package fi.questionofday.android.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.presenter.MainActivityPresenter;
import fi.questionofday.android.ui.util.AnimationUtils;

public class MainActivity extends BaseActivity implements MainActivityPresenter.MainActivityView {

    private static final String SUPER_SECRET_PASSWORD = "gravity4ever";
    private MainActivityPresenter presenter;
    final Random random = new Random();
    final int minDelay = 150;
    final int minDuration = 100;

    @BindView(R.id.a_main_question_of_the_day) TextView textQuestion;
    @BindView(R.id.a_main_button_super_happy) ImageView buttonSuperHappy;
    @BindView(R.id.a_main_button_happy) ImageView buttonHappy;
    @BindView(R.id.a_main_button_meh) ImageView buttonMeh;
    @BindView(R.id.a_main_button_sad) ImageView buttonSad;
    @BindView(R.id.a_main_zalando_logo) ImageView zalandoLogo;

    private Question question;
    private boolean waitForResponse;

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
        if (question != null) {
            animateViews();
        }
        waitForResponse = false;
    }

    private void animateViews() {
        //Delay until ready to animate:
        buttonSuperHappy.post(() -> {
            animateView(buttonSuperHappy);
            animateView(buttonHappy);
            animateView(buttonMeh);
            animateView(buttonSad);
            zalandoLogo.animate().alpha(0.2f).start();
        });
    }

    private void animateView(View view) {
        //Reset values:
        view.setScaleX(0);
        view.setScaleY(0);
        view.setAlpha(1f);
        //Animate:
        view.animate()
                .scaleY(1f)
                .scaleX(1f)
                .setDuration(random.nextInt(100) + minDuration)
                .setStartDelay(random.nextInt(100) + minDelay)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @Override
    public void openStatistics() {
        StatisticsActivity.launch(this);
    }

    @Override
    public void sayThanks(Feedback.FEEDBACK feedback) {

        switch (feedback) {
            case ONE:
                ThanksActivity.launch(this, zalandoLogo, buttonSad);
                break;
            case TWO:
                ThanksActivity.launch(this, zalandoLogo, buttonMeh);
                break;
            case THREE:
                ThanksActivity.launch(this, zalandoLogo, buttonHappy);
                break;
            case FOUR:
                ThanksActivity.launch(this, zalandoLogo, buttonSuperHappy);
                break;
        }
    }

    @Override
    public void showQuestion(Question question) {
        this.question = question;
        textQuestion.setAlpha(0);
        textQuestion.setText(question.getText());
        textQuestion.animate()
                .scaleY(1f)
                .scaleX(1f)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        animateViews();
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.a_main_button_statistics})
    public void onStatisticsClick(View view) {
        openStatistics();
    }

    @OnClick({R.id.a_main_button_change_question})
    public void onChangeQuestionClick(View view) {

        @SuppressLint("InflateParams")
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.d_question, null);
        final EditText pass = ButterKnife.findById(layout, R.id.d_question_password);
        final EditText edit = ButterKnife.findById(layout, R.id.d_question_edit);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.enter_the_question)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, null) //Assigned later
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.setOnShowListener(dialog1 -> {

            Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view1 -> {
                final String password = pass.getText().toString();
                if (!SUPER_SECRET_PASSWORD.equals(password)) {
                    Toast.makeText(getApplicationContext(), R.string.error_wrong_password,
                            Toast.LENGTH_SHORT).show();
                    pass.requestFocus();
                    return;
                }

                final String text = edit.getText().toString().trim();
                //No question, no submit
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(getApplicationContext(), R.string.error_empty_question,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                getPresenter().submitQuestion(text);
                //Dismiss once everything is OK.
                dialog1.dismiss();
            });
        });
        dialog.show();
    }

    @Override
    public void onQuestionSubmittedSuccessfully() {
        Toast.makeText(getApplicationContext(), R.string.question_set_success,
                Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.a_main_button_super_happy,
            R.id.a_main_button_happy,
            R.id.a_main_button_meh,
            R.id.a_main_button_sad})
    public void onEmoticonClick(ImageView view) {
        if (waitForResponse) {
            return;
        }
        waitForResponse = true;
        AnimationUtils.clickAnimation(view, () -> {

            switch (view.getId()) {
                case R.id.a_main_button_super_happy:
                    getPresenter().submitResult(question, Feedback.FEEDBACK.FOUR);
                    break;
                case R.id.a_main_button_happy:
                    getPresenter().submitResult(question, Feedback.FEEDBACK.THREE);
                    break;
                case R.id.a_main_button_meh:
                    getPresenter().submitResult(question, Feedback.FEEDBACK.TWO);
                    break;
                case R.id.a_main_button_sad:
                    getPresenter().submitResult(question, Feedback.FEEDBACK.ONE);
                    break;
            }
        });
    }
}
