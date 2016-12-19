package fi.howislife.android.ui.domain.entity;

/**
 * Created by plappalainen on 19/12/2016.
 */

public class Question {

    private final int id;
    private final String text;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    //constructor method
    public Question(int id, String text) {
        this.id = id;
        this.text = text;

    }
}
