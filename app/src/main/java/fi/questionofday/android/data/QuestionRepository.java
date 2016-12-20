package fi.questionofday.android.data;

import java.util.List;

import fi.questionofday.android.domain.entity.Question;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class QuestionRepository {

    private static QuestionRepository instance = new QuestionRepository();

    public static QuestionRepository getInstance() {
        return instance;
    }

    private QuestionRepository() {
    }

    public Observable<Question> loadCurrentQuestion() {
        return Observable.never();
    }

    public Completable submitFeedback(Object question, Object feedback) {
        return Completable.never();
    }

    public Observable<List<Object>> loadQuestionsFeedback() {
        return Observable.never();
    }

    public Observable<Question> loadFeedbackForQuestion(int questionId) {
        return Observable.never();
    }
}
