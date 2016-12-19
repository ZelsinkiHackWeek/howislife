package fi.questionofday.android.domain;

import java.util.List;

import fi.questionofday.android.data.QuestionRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public Observable<Object> loadCurrentQuestion() {
        return repository.loadCurrentQuestion().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> loadFeedbackForQuestion(int questionId) {
        return repository.loadFeedbackForQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Object>> loadQuestionsFeedback() {
        return repository.loadQuestionsFeedback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable submitFeedback(Object question, Object feedback) {
        return repository.submitFeedback(question, feedback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
