package fi.howislife.android.ui.presenter;

import fi.howislife.android.domain.QuestionService;
import fi.howislife.android.helper.SubscriptionHelper;

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.MainActivityView> {

    private final QuestionService questionService;

    public MainActivityPresenter(SubscriptionHelper subscriptionHelper,
                                 QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    public interface MainActivityView extends BasePresenter.View {
        void showSomething();
    }
}
