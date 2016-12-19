package fi.questionofday.android.ui.presenter;

import fi.questionofday.android.domain.QuestionService;
import fi.questionofday.android.helper.SubscriptionHelper;

public class StatisticsActivityPresenter extends
        BasePresenter<StatisticsActivityPresenter.StatisticsActivityView> {
    private final QuestionService questionService;

    public StatisticsActivityPresenter(SubscriptionHelper subscriptionHelper,
                                       QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    public void loadStuff() {
        //Load stuff
    }

    public interface StatisticsActivityView extends BasePresenter.View {
        void openStatistics();
    }
}
