package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

    // clean all elements of the recycler view
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

//    // add a list of items
//    public void addAll(List<Tweet> lst) {
//        tweets.addAll(lst);
//        notifyDataSetChanged();
//    }

    // define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPfp;
        TextView tvBody;
        TextView tvName;
        TextView tvScreenName;
        TextView tvDate;
        ImageView ivEmbed;
        ImageView ivReply;
        ImageView ivRetweet;
        ImageView ivLike;

        /**
         *
         * @param itemView
         */
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivPfp = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivEmbed = itemView.findViewById(R.id.ivEmbed);
            ivReply = itemView.findViewById(R.id.ivReply);
            ivRetweet = itemView.findViewById(R.id.ivRetweet);
            ivLike = itemView.findViewById(R.id.ivLike);
        }

        /**
         * binds a Tweet object into the item view
         * @param t a Tweet object that contains the body, timestamp, and user information
         */
        public void bind(Tweet t) {
            tvBody.setText(t.getBody());
            tvName.setText(t.getUser().getName());
            tvScreenName.setText(t.getUser().getScreenName());
            tvDate.setText(t.getRelativeTimeAgo());

            Glide.with(context)
                    .load(t.getUser().getPfpUrl())
                    .transform(new CircleCrop())
                    .into(ivPfp);

            int radius = 30;
            int margin = 10;
            String embedUrl = null;
            embedUrl = t.getEmbedUrl();
            Log.i("TweetsAdapter", t.getUser().getName() + " imageUrl: " + embedUrl);
            if (embedUrl != null) {
                Glide.with(context)
                        .load(embedUrl)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .override(700)
                        .into(ivEmbed);
                ivEmbed.setVisibility(View.VISIBLE);
            }

            int replyRes = context.getResources().getIdentifier("@drawable/ic_reply", null, context.getPackageName());
            Drawable reply = context.getResources().getDrawable(replyRes);
            ivReply.setImageDrawable(reply);

            int retweetRes = context.getResources().getIdentifier("@drawable/ic_vector_retweet_stroke", null, context.getPackageName());
            Drawable retweet = context.getResources().getDrawable(retweetRes);
            ivRetweet.setImageDrawable(retweet);

            int likeRes = context.getResources().getIdentifier("@drawable/ic_vector_heart_stroke", null, context.getPackageName());
            Drawable like = context.getResources().getDrawable(likeRes);
            ivLike.setImageDrawable(like);
        }
    }
}
