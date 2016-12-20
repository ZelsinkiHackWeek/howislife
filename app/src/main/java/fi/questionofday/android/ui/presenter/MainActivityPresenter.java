package fi.questionofday.android.ui.presenter;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.helper.SubscriptionHelper;
import io.reactivex.functions.Action;
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
        subscriptionHelper.addSubscription(questionService.loadCurrentQuestion().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getView().showQuestion(null);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().showError();
            }
        }));
    }

    public void submitResult() {
        questionService.submitFeedback(null, null);
    }

    public void createQuestion(String s) {

        subscriptionHelper.addSubscription(
                questionService.submitQuestion(s).subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("a");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("error");
                    }
                })
        );
    }

    public interface MainActivityView extends BasePresenter.View {
        void openStatistics();

        void showQuestion(Question question);

        void showError();
    }
}
