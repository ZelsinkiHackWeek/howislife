package fi.questionofday.android.data.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class QuestionData {

    public String id;
    public String text;
    public Long creationDate;
    public List<Integer> stars;

    public QuestionData() {
        // Default constructor required for calls to DataSnapshot.getValue(QuestionData.class)
    }

    public QuestionData(String id, String text, Long creationDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.stars = new ArrayList<>(4);
    }

}
