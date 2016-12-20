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
import fi.questionofday.android.domain.entity.Feedback;
import fi.questionofday.android.ui.presenter.StatisticsActivityPresenter;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private final List<Feedback> feedbackList;
    private final StatisticsActivityPresenter presenter;

    public FeedbackAdapter(List<Feedback> feedbackList,
                           StatisticsActivityPresenter presenter) {
        this.feedbackList = feedbackList;
        this.presenter = presenter;
    }

    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .vh_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.questionTitle.setText(feedbackList.get(position).getQuestion().getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vh_feedback_questiontitle) TextView questionTitle;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vh_feedback_questiontitle)
        void clickFeedbackItem() {
            presenter.onFeedbackClicked(feedbackList.get(getLayoutPosition()));
        }
    }
}
