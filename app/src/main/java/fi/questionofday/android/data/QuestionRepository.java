package fi.questionofday.android.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.fernandocejas.arrow.optional.Optional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fi.questionofday.android.data.entity.QuestionData;
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.domain.entity.Question;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

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

    public Observable<Optional<Question>> loadCurrentQuestion() {

        return Observable.create(new ObservableOnSubscribe<Optional<QuestionData>>() {

            @Override
            public void subscribe(ObservableEmitter<Optional<QuestionData>> e) throws Exception {

                ValueEventListener valueListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // We only need firs item from all the questions
                        Iterator<DataSnapshot> dataSnapshotIterator =
                                dataSnapshot.getChildren().iterator();
                        if (dataSnapshotIterator.hasNext()) {
                            QuestionData questionData = dataSnapshotIterator.next()
                                    .getValue(QuestionData.class);
                            e.onNext(Optional.of(questionData));
                        } else {
                            e.onNext(Optional.absent());
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

            }
        }).map(new Function<Optional<QuestionData>, Optional<Question>>() {
            @Override
            public Optional<Question> apply(Optional<QuestionData> questionDataOpt) throws
                    Exception {
                if (questionDataOpt.isPresent()) {
                    return Optional.of(
                            new Question(questionDataOpt.get().id, questionDataOpt.get().text));
                }
                return Optional.absent();
            }
        }).distinctUntilChanged();
    }

    public Completable submitQuestion(String textOfQuestion) {

        return Completable.fromAction(() -> {
            QuestionData newQuestionData = new QuestionData(String.valueOf(System
                    .currentTimeMillis()), textOfQuestion, System.currentTimeMillis());
            questionsTable.child(newQuestionData.id).setValue(newQuestionData);
        });
    }

    public Completable submitFeedback(Question question, Feedback.FEEDBACK feedback) {

        return Completable.create(c ->

                questionsTable.child(question.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        QuestionData questionData = dataSnapshot.getValue(QuestionData.class);
                        Integer currentStars;

                        if (questionData.stars == null) {
                            questionData.stars = new ArrayList<>(4);
                        }

                        switch (feedback) {
                            case ONE:
                                currentStars = questionData.stars.get(0);
                                currentStars += 1;
                                questionData.stars.set(0, currentStars);
                                break;
                            case TWO:
                                currentStars = questionData.stars.get(1);
                                currentStars += 1;
                                questionData.stars.set(1, currentStars);
                                break;
                            case THREE:
                                currentStars = questionData.stars.get(2);
                                currentStars += 1;
                                questionData.stars.set(2, currentStars);
                                break;
                            case FOUR:
                                currentStars = questionData.stars.get(3);
                                currentStars += 1;
                                questionData.stars.set(3, currentStars);
                                break;
                        }
                        questionsTable.child(questionData.id).setValue(questionData);
                        c.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        c.onError(databaseError.toException());
                    }
                }));
    }

    public Observable<List<Question>> loadQuestions() {

        return Observable.create(new ObservableOnSubscribe<List<Question>>() {

            @Override
            public void subscribe(ObservableEmitter<List<Question>> e) throws Exception {

                ValueEventListener valueListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Question> questionList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            QuestionData questionData = snapshot.getValue(QuestionData.class);
                            questionList.add(new Question(questionData.id, questionData.text));
                        }
                        Collections.reverse(questionList);
                        e.onNext(questionList);
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

                questionsTable.orderByChild("creationDate").addValueEventListener(valueListener);

            }
        }).distinctUntilChanged();
    }

    public Observable<Optional<Feedback>> loadFeedback(Question question) {

        return Observable.create(new ObservableOnSubscribe<Optional<QuestionData>>() {
            @Override
            public void subscribe(ObservableEmitter<Optional<QuestionData>> e) throws Exception {

                ValueEventListener valueListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // We only need firs item from all the questions
                        Iterator<DataSnapshot> dataSnapshotIterator =
                                dataSnapshot.getChildren().iterator();
                        if (dataSnapshotIterator.hasNext()) {
                            QuestionData questionData = dataSnapshotIterator.next()
                                    .getValue(QuestionData.class);
                            e.onNext(Optional.of(questionData));
                        } else {
                            e.onNext(Optional.absent());
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

                questionsTable.child(question.getId())
                        .limitToFirst(1)
                        .addValueEventListener(valueListener);
            }
        }).map(questionDataOptional -> {
            if (questionDataOptional.isPresent()) {
                QuestionData questionData = questionDataOptional.get();
                return Optional.of(new Feedback(
                        new Question(
                                questionData.id,
                                questionData.text
                        ), questionData.stars.get(0),
                        questionData.stars.get(1),
                        questionData.stars.get(2),
                        questionData.stars.get(3)));
            } else {
                return Optional.absent();
            }
        });
    }
}
