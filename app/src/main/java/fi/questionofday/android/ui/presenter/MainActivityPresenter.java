package fi.questionofday.android.ui.presenter;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.helper.SubscriptionHelper;
import io.reactivex.functions.Consumer;

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.MainActivityView> {

    private final QuestionService questionService;

    public MainActivityPresenter(SubscriptionHelper subscriptionHelper,
                                 QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    @Override
    public void initialize() {
        subscriptionHelper.addSubscription(questionService.loadCurrentQuestion()
                .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getView().showQuestion((Question) o);
            }
        }, throwable -> getView().showError()));
    }

    public void submitQuestion(String question) {
        questionService.submitQuestion(question).subscribe();
    }

    public void submitResult() {
        questionService.submitFeedback(null, 0);
    }

    public interface MainActivityView extends BasePresenter.View {
        void openStatistics();

        void showQuestion(Question question);

        void showError();
    }
}
