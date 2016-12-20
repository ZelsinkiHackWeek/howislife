package fi.questionofday.android.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import fi.questionofday.android.data.entity.QuestionData;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class QuestionRepository {

    private static final String QUESTIONS_TABLE_NAME = "questions";

    private static QuestionRepository instance = new QuestionRepository();
    private final DatabaseReference firebaseDatabase;

    public static QuestionRepository getInstance() {
        return instance;
    }

    private QuestionRepository() {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Observable<Object> loadCurrentQuestion() {

        return Observable.create(e -> {

            final DatabaseReference questionsTable = firebaseDatabase.child(QUESTIONS_TABLE_NAME);

            ValueEventListener valueListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // We only need firs item from all the questions
                    Iterator<DataSnapshot> dataSnapshotIterator =
                            dataSnapshot.getChildren().iterator();
                    if (dataSnapshotIterator.hasNext()) {
                        DataSnapshot dataSnapshotItem = dataSnapshotIterator.next();
                        e.onNext(dataSnapshotItem.getValue(QuestionData.class));
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
                    .limitToFirst(1)
                    .addValueEventListener(valueListener);
        });
    }

    public Completable submitFeedback(Object question, Object feedback) {
        return Completable.never();
    }

    public Observable<List<Object>> loadQuestionsFeedback() {
        return Observable.never();
    }

    public Observable<Object> loadFeedbackForQuestion(int questionId) {
        return Observable.never();
    }
}
