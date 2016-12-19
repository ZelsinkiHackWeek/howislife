package fi.questionofday.android.helper;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SubscriptionHelper {

    private CompositeDisposable subscriptions;

    public SubscriptionHelper() {
        subscriptions = new CompositeDisposable();
    }

    public void addSubscription(@NonNull Disposable... disposablesToAdd) {
        for (Disposable disposable : disposablesToAdd) {
            subscriptions.add(disposable);
        }
    }

    public boolean hasSubscriptions() {
        return subscriptions.size() > 0;
    }

    public void unsubscribeAll() {
        subscriptions.clear();
    }
}
