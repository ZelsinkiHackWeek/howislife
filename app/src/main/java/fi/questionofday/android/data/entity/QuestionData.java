package fi.questionofday.android.data.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class QuestionData {

    public String id;
    public String text;
    public Long creationDate;

    public QuestionData() {
        // Default constructor required for calls to DataSnapshot.getValue(QuestionData.class)
    }

    public QuestionData(String id, String text) {
        this.id = id;
        this.text = text;
    }

}
