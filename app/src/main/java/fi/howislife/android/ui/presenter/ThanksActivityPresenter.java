package fi.howislife.android.ui.presenter;

import fi.howislife.android.domain.QuestionService;
import fi.howislife.android.helper.SubscriptionHelper;

public class ThanksActivityPresenter extends BasePresenter<ThanksActivityPresenter.ThanksActivityView> {

    private final QuestionService questionService;

    public ThanksActivityPresenter(SubscriptionHelper subscriptionHelper,
                                   QuestionService questionService) {
        super(subscriptionHelper);
        this.questionService = questionService;
    }

    public void loadStuff() {
        //Load stuff
    }

    public interface ThanksActivityView extends BasePresenter.View {
        void openStatistics();
    }
}
