package fi.questionofday.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fi.questionofday.android.R;
import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.domain.entity.Question;
import fi.questionofday.android.ui.presenter.MainActivityPresenter;
import fi.questionofday.android.ui.util.AnimationUtils;

public class MainActivity extends BaseActivity implements MainActivityPresenter.MainActivityView {

    private MainActivityPresenter presenter;

    @BindView(R.id.a_main_question_of_the_day) TextView textQuestion;
    @BindView(R.id.a_main_button_super_happy) TextView buttonSuperHappy;
    @BindView(R.id.a_main_button_happy) TextView buttonHappy;
    @BindView(R.id.a_main_button_meh) TextView buttonMeh;
    @BindView(R.id.a_main_button_sad) TextView buttonSad;

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
        buttonSuperHappy.setText(new String(Character.toChars(0x1F601)));
        buttonHappy.setText(new String(Character.toChars(0x1F60A)));
        buttonMeh.setText(new String(Character.toChars(0x1F612)));
        buttonSad.setText(new String(Character.toChars(0x1F622)));
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

    @OnClick({R.id.a_main_button_statistics})
    public void onStatisticsClick(View view) {
        openStatistics();
    }

    @OnClick({R.id.a_main_button_super_happy,
            R.id.a_main_button_happy,
            R.id.a_main_button_meh,
            R.id.a_main_button_sad})
    public void onEmoticonClick(View view) {
        AnimationUtils.clickAnimation(view, () -> getPresenter().submitResult());
        ThanksActivity.launch(this);
    }
}
