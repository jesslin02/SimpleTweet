package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    /**
     * pass in context and list of tweets
     */
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    /**
     * for each row, inflate a layout
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view); // using the view holder that we defined below
    }

    /**
     * bind values based on the position of the element
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // get data at the position
        Tweet t = tweets.get(position);
        // bind the tweet with the view holder
        holder.bind(t);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPfp;
        TextView tvBody;
        TextView tvName;
        TextView tvScreenName;
        TextView tvDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivPfp = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(Tweet t) {
            tvBody.setText(t.body);
            tvName.setText(t.user.name);
            tvScreenName.setText(t.user.screenName);
            tvDate.setText(t.getRelativeTimeAgo());
            Glide.with(context)
                    .load(t.user.pfpUrl)
                    .into(ivPfp);
        }
    }
}
