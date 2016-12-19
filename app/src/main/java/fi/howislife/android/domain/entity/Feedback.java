package fi.howislife.android.domain.entity;

/**
 * Created by plappalainen on 19/12/2016.
 */

public class Feedback {

    private final int feedback;

    public int getFeedback() {
        return feedback;
    }

    public Feedback(int feedback) {
        this.feedback = feedback;
    }
}
