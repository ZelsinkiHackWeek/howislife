package fi.questionofday.android.ui.presenter;

import java.util.List;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.helper.SubscriptionHelper;

public class StatisticsActivityPresenter extends
        BasePresenter<StatisticsActivityPresenter.StatisticsActivityView> {

    private final QuestionService questionService;

    public StatisticsActivityPresenter(SubscriptionHelper subscriptionHelper,
                                       QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    @Override
    public void initialize() {
        subscriptionHelper.addSubscription(
                questionService.loadQuestionsFeedback()
                        .subscribe(feedbacks -> getView().showFeedback(feedbacks),
                                throwable -> {
                                    getView().showError();
                                })
        );
    }

    public void onFeedbackClicked(Feedback feedback) {
        getView().showFeedback(feedback);
    }

    public interface StatisticsActivityView extends BasePresenter.View {
        void showError();

        void showFeedback(List<Feedback> feedbackList);

        void showFeedback(Feedback feedbackToShow);
    }
}
