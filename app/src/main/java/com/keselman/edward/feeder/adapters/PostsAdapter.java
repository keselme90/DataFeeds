package com.keselman.edward.feeder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.keselman.edward.feeder.R;
import com.keselman.edward.feeder.adapters.viewholders.LinkViewHolder;
import com.keselman.edward.feeder.adapters.viewholders.VideoViewHolder;
import com.keselman.edward.feeder.models.Entry;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable
{
    private List<Entry> mPostList;
    private List<Entry> mPostListFull;
    private static int TYPE_LINK = 1;
    private static int TYPE_VIDEO = 2;
    private Context mContext;

    public PostsAdapter(Context context, List<Entry> posts){

        this.mContext = context;
        this.mPostList = posts;
        Log.d(TAG, "PostsAdapter: ");
    }

    @Override
    public int getItemCount()
    {
        return mPostList.size();
//        return mPostListFiltered.size();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        if(viewType == TYPE_LINK){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_link_relative_row, viewGroup, false);
            return new LinkViewHolder(view);
        }
        else{
            view = LayoutInflater.from(mContext).inflate(R.layout.item_video_relative_row, viewGroup, false);
            return new VideoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        Animation anim;
        if(position % 2 == 0)
            anim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right);
        else
            anim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left);

        if (getItemViewType(position) == TYPE_LINK) {
            ((LinkViewHolder) viewHolder).setDetails(mContext, mPostList.get(position));
            ((LinkViewHolder) viewHolder).mCardView.setAnimation(anim);
        } else {
            ((VideoViewHolder) viewHolder).setDetails(mContext, mPostList.get(position));
            ((VideoViewHolder) viewHolder).mCardView.setAnimation(anim);
        }

    }

    @Override
    public int getItemViewType(int position) {

        return mPostList.get(position).getType().getValue().toLowerCase().equals("link") ? TYPE_LINK : TYPE_VIDEO;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchQuery = charSequence.toString();
                List<Entry> filtered = new ArrayList<>();
                if(mPostListFull != null) {
                    if (searchQuery.isEmpty()) {
                        filtered.addAll(mPostListFull);
                    } else {
                        for (Entry entry : mPostListFull) {
                            if (entry.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                                filtered.add(entry);
                            }
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mPostList.clear();
                mPostList.addAll((List)filterResults.values);

                notifyDataSetChanged();
            }
        };
    }

    public void setFullList() {

        this.mPostListFull = new ArrayList<>(mPostList);
    }
}
