package com.aravindakv.codetest.itunessearch.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import codetest.aravindakv.com.itunessearch.R;

/**
 * Created by aravindakv on 14/02/18.
 */

public class ITunesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<MainListItem> recycleViewListItems;
    private View.OnClickListener onClickListener;
    private Context mContext;

    public ITunesListAdapter(Context context, List<MainListItem> recycleViewListItems) {
        this.mContext = context;
        this.recycleViewListItems = recycleViewListItems;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView artistName = holder.itemView.findViewById(R.id.artistName);
        TextView trackName = holder.itemView.findViewById(R.id.trackName);
        ImageView previewImage = holder.itemView.findViewById(R.id.previewImage);

        MainListItem listItem = recycleViewListItems.get(position);
        artistName.setText(listItem.getArtistName());
        trackName.setText(listItem.getTrackName());
        Picasso.with(mContext).load(listItem.getImageUrl()).into(previewImage);
    }

    @Override
    public int getItemCount() {
        return recycleViewListItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView artistName, trackName;
        private ImageView previewImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.artistName);
            trackName = itemView.findViewById(R.id.trackName);
            previewImage = itemView.findViewById(R.id.previewImage);
        }
    }


}
