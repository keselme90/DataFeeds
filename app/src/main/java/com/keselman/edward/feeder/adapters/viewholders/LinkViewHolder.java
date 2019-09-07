package com.keselman.edward.feeder.adapters.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keselman.edward.feeder.R;
import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Post;

public class LinkViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView mTitle, mSummary;

    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mTitle = itemView.findViewById(R.id.link_title);
        this.mSummary = itemView.findViewById(R.id.link_summary);
    }

    public void setDetails(Entry entry){

        this.mTitle.setText(entry.getTitle());
        this.mSummary.setText(entry.getSummary());
    }
}
