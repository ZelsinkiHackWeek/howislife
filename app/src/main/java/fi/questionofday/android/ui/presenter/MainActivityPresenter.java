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
        questionService.loadCurrentQuestion().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getView().showQuestion(null);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showError();
            }
        });
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
