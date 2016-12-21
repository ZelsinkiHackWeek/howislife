package fi.questionofday.android.ui.presenter;

import android.support.annotation.Nullable;

import java.util.List;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.helper.SubscriptionHelper;
import io.reactivex.disposables.Disposable;

public class StatisticsActivityPresenter extends
        BasePresenter<StatisticsActivityPresenter.StatisticsActivityView> {

    private final QuestionService questionService;
    @Nullable private Disposable questionFeedBackDisposable;

    public StatisticsActivityPresenter(SubscriptionHelper subscriptionHelper,
                                       QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    @Override
    public void initialize() {
        subscriptionHelper.addSubscription(
                questionService.loadQuestions()
                        .subscribe(questions -> getView().showQuestions(questions),
                                throwable -> {
                                    getView().showError();
                                })
        );
    }

    public void onQuestionClicked(Question question) {

        if (questionFeedBackDisposable != null && !questionFeedBackDisposable.isDisposed()) {
            questionFeedBackDisposable.dispose();
        }

        questionFeedBackDisposable = questionService.loadFeedback(question)
                .subscribe(feedbackOptional -> {
                    getView().showFeedback(feedbackOptional.orNull());
                });

        subscriptionHelper.addSubscription(questionFeedBackDisposable);
    }

    public interface StatisticsActivityView extends BasePresenter.View {
        void showError();

        void showQuestions(List<Question> questionList);

        void showFeedback(@Nullable Feedback feedbackToShow);
    }
}
