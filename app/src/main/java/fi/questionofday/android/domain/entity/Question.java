package fi.questionofday.android.domain.entity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question = (Question) o;

        return id != null ? id.equals(question.id) : question.id == null && (text != null ? text
                .equals(question.text) : question.text == null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
