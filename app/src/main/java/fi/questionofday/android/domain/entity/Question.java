package fi.questionofday.android.domain.entity;

/**
 * Created by plappalainen on 19/12/2016.
 */

public class Question {

    private final String id;
    private final String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    //constructor method
    public Question(String id, String text) {
        this.id = id;
        this.text = text;

    }
}
