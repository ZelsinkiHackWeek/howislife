package fi.questionofday.android.domain;

import com.fernandocejas.arrow.optional.Optional;

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

    public Observable<Optional<Question>> loadCurrentQuestion() {
        return repository.loadCurrentQuestion().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Question>> loadQuestions() {
        return repository.loadQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Optional<Feedback>> loadFeedback(Question question) {
        return repository.loadFeedback(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable submitFeedback(Question question, Feedback.FEEDBACK feedback) {
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
