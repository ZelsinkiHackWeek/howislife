package fi.questionofday.android.domain.entity;

public class Feedback {

    public enum FEEDBACK {
        ONE, TWO, THREE, FOUR;

    }

    private final Question question;

    private final int star1;

    private final int star2;
    private final int star3;
    private final int star4;

    public Feedback(Question question, int star1, int star2, int star3, int star4) {
        this.question = question;
        this.star2 = star2;
        this.star3 = star3;
        this.star1 = star1;
        this.star4 = star4;
    }

    public int getStar1() {
        return star1;
    }

    public int getStar2() {
        return star2;
    }

    public int getStar3() {
        return star3;
    }

    public int getStar4() {
        return star4;
    }

    public int getTotal() {
        return star1 + star2 + star3 + star4;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Feedback feedback = (Feedback) o;

        if (star1 != feedback.star1) {
            return false;
        }
        if (star2 != feedback.star2) {
            return false;
        }
        if (star3 != feedback.star3) {
            return false;
        }
        if (star4 != feedback.star4) {
            return false;
        }
        return question != null ? question.equals(feedback.question) : feedback.question == null;
    }


    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + star1;
        result = 31 * result + star2;
        result = 31 * result + star3;
        result = 31 * result + star4;
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "question=" + question +
                ", star1=" + star1 +
                ", star2=" + star2 +
                ", star3=" + star3 +
                ", star4=" + star4 +
                '}';
    }
}
