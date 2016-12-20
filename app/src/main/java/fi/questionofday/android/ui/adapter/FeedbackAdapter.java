package fi.questionofday.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fi.questionofday.android.R;
import fi.questionofday.android.domain.entity.Feedback;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private List<Feedback> feedbackList;

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitle;

        ViewHolder(View v) {
            super(v);
            questionTitle = (TextView) v.findViewById(R.id.vh_feedback_questiontitle);
        }
    }
}
