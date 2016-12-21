package fi.questionofday.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.questionofday.android.R;
import fi.questionofday.android.domain.entity.Question;
import fi.questionofday.android.ui.presenter.StatisticsActivityPresenter;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final List<Question> questionList;
    private final StatisticsActivityPresenter presenter;

    public QuestionAdapter(List<Question> questionList,
                           StatisticsActivityPresenter presenter) {
        this.questionList = questionList;
        this.presenter = presenter;
    }

    public void setItems(List<Question> items) {
        questionList.clear();
        questionList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .vh_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.questionTitle.setText(questionList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vh_feedback_questiontitle) TextView questionTitle;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vh_feedback_questiontitle)
        void clickFeedbackItem() {
            presenter.onQuestionClicked(questionList.get(getLayoutPosition()));
        }
    }
}
