package fi.questionofday.android.domain.entity;

/**
 * Created by plappalainen on 19/12/2016.
 */

public class Feedback {

    private final int star1;
    private final int star2;
    private final int star3;
    private final int star4;
    private final int total;

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
        return total;
    }

    public Feedback(int feedback, int star1, int star2, int star3, int star4, int total) {
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.total = total;
    }
}
