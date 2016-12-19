package fi.howislife.android.ui.presenter;

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.MainActivityView> {

    public MainActivityPresenter() {
    }

    public interface MainActivityView extends BasePresenter.View {
        void showSomething();
    }
}
