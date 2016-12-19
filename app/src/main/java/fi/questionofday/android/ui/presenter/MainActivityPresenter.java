package fi.questionofday.android.ui.presenter;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;
import fi.questionofday.android.ui.domain.entity.Question;

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.MainActivityView> {

    private final QuestionService questionService;

    public MainActivityPresenter(SubscriptionHelper subscriptionHelper,
                                 QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    @Override
    public void initialize() {
        questionService.loadCurrentQuestion();
    }

    public void submitResult() {
        questionService.submitFeedback(null, null);
    }

    public interface MainActivityView extends BasePresenter.View {
        void openStatistics();

        void showQuestion(Question question);
    }
}
