package com.keselman.edward.feeder.adapters.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.keselman.edward.feeder.R;
import com.keselman.edward.feeder.models.Entry;
import com.squareup.picasso.Picasso;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView mTitle, mSummary;
    public AppCompatImageView mImage;
    public CardView mCardView;
    public AppCompatTextView mDate;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mTitle = itemView.findViewById(R.id.video_title);
        this.mSummary = itemView.findViewById(R.id.video_summary);
        this.mCardView = itemView.findViewById(R.id.card_view_video);
        this.mImage = itemView.findViewById(R.id.image);
        this.mDate = itemView.findViewById(R.id.publishedOn);
    }

    public void setDetails(Context context, Entry entry){

        SpannableString content = new SpannableString(entry.getTitle());
        content.setSpan(new UnderlineSpan(), 0, entry.getTitle().length(), 0);
        this.mTitle.setText(content);
        this.mSummary.setText(entry.getSummary());
        if(entry.getPublished() != null && !entry.getPublished().isEmpty())
            this.mDate.setText(entry.getPublished());
        else
            this.mDate.setText("");

        String imgUrl = entry.getMedia_group().get(0).getmediaItem().get(0).getSrc();
        if(imgUrl != null && !imgUrl.isEmpty())
            Picasso.with(context).load(imgUrl).
                    resize(300,300).into(this.mImage);
    }
}
