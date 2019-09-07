package com.keselman.edward.feeder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.keselman.edward.feeder.R;
import com.keselman.edward.feeder.adapters.viewholders.LinkViewHolder;
import com.keselman.edward.feeder.adapters.viewholders.VideoViewHolder;
import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Entry> mPostList;
    private List<Entry> mPostListFiltered;
    private static int TYPE_LINK = 1;
    private static int TYPE_VIDEO = 2;
    private Context mContext;

    public PostsAdapter(Context context, List<Entry> posts){

        this.mContext = context;
        this.mPostList = posts;
        this.mPostListFiltered = posts;

    }

    @Override
    public int getItemCount() {
        return mPostListFiltered.size();
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

        if (getItemViewType(position) == TYPE_LINK) {
            ((LinkViewHolder) viewHolder).setDetails(mPostListFiltered.get(position));
        } else {
            ((VideoViewHolder) viewHolder).setDetails(mPostListFiltered.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {

        return mPostListFiltered.get(position).getType().getValue().toLowerCase().equals("link") ? TYPE_LINK : TYPE_VIDEO;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchQuery = charSequence.toString();
                if(searchQuery.isEmpty()){
                    mPostListFiltered = mPostList;
                }
                else{
                    List<Entry> filtered = new ArrayList<>();
                    for(Entry entry : mPostList){
                        if(entry.getTitle().toLowerCase().contains(searchQuery.toLowerCase())){
                            filtered.add(entry);
                        }
                    }
                    mPostListFiltered = filtered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPostListFiltered;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mPostListFiltered = (ArrayList<Entry>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
