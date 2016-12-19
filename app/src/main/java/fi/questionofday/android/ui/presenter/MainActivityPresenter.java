package fi.questionofday.android.ui.presenter;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.MainActivityView> {

    private final QuestionService questionService;

    public MainActivityPresenter(SubscriptionHelper subscriptionHelper,
                                 QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    public void submitResult() {
        //Submit stuff
    }

    public interface MainActivityView extends BasePresenter.View {
        void openStatistics();
    }
}
