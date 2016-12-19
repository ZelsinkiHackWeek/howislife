package fi.howislife.android.ui.activity;

import android.os.Bundle;

import fi.howislife.android.R;
import fi.howislife.android.data.QuestionRepository;
import fi.howislife.android.domain.QuestionService;
import fi.howislife.android.helper.SubscriptionHelper;
import fi.howislife.android.ui.presenter.BasePresenter;
import fi.howislife.android.ui.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivity implements MainActivityPresenter.MainActivityView {

    @Override
    public BasePresenter getPresenter() {
        // Add here all dependencies to the presenter
        return new MainActivityPresenter(new SubscriptionHelper(),
                new QuestionService(
                        QuestionRepository.getInstance()
                ));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showSomething() {

    }
}
