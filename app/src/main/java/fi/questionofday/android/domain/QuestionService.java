package fi.questionofday.android.domain;

import java.util.List;

import fi.questionofday.android.data.QuestionRepository;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.domain.entity.Question;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public Observable<Question> loadCurrentQuestion() {
        return repository.loadCurrentQuestion().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Question> loadFeedbackForQuestion(int questionId) {
        return repository.loadFeedbackForQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Feedback>> loadQuestionsFeedback() {
        return repository.loadQuestionsFeedback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable submitFeedback(Question question, int feedback) {
        return repository.submitFeedback(question, feedback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable submitQuestion(String text) {
        return repository.submitQuestion(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
