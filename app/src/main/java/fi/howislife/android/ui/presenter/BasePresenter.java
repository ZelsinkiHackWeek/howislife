package fi.howislife.android.ui.presenter;

public abstract class BasePresenter<T extends BasePresenter.View> {

    private T view;

    public final T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public void initialize() {
    }

    public void pause() {
    }

    public void destroy() {
    }

    public void resume() {
    }

    public interface View {
    }

}
