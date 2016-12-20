package fi.questionofday.android.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import fi.questionofday.android.data.entity.QuestionData;
import fi.questionofday.android.domain.entity.Question;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class QuestionRepository {

    private static final String QUESTIONS_TABLE_NAME = "questions";

    private static QuestionRepository instance = new QuestionRepository();
    private final DatabaseReference questionsTable;

    public static QuestionRepository getInstance() {
        return instance;
    }

    private QuestionRepository() {
        questionsTable = FirebaseDatabase.getInstance().getReference()
                .child(QUESTIONS_TABLE_NAME);
    }

    public Observable<Question> loadCurrentQuestion() {

        return Observable.create(e -> {

            ValueEventListener valueListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // We only need firs item from all the questions
                    Iterator<DataSnapshot> dataSnapshotIterator =
                            dataSnapshot.getChildren().iterator();
                    if (dataSnapshotIterator.hasNext()) {
                        QuestionData questionData = dataSnapshotIterator.next()
                                .getValue(QuestionData.class);
                        e.onNext(questionData);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onError(databaseError.toException());
                }
            };

            e.setCancellable(() -> {
                // should unsubscribe here
                questionsTable.removeEventListener(valueListener);
            });

            questionsTable.orderByChild("creationDate")
                    .limitToLast(1)
                    .addValueEventListener(valueListener);
        }).map(object -> {
            QuestionData questionData = (QuestionData) object;
            return new Question(questionData.id, questionData.text);
        });
    }

    public Completable submitQuestion(String textOfQuestion) {

        return Completable.fromAction(() -> {
            QuestionData newQuestionData = new QuestionData(String.valueOf(System
                    .currentTimeMillis()), textOfQuestion, System.currentTimeMillis());
            questionsTable.child(newQuestionData.id).setValue(newQuestionData);
        });
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
