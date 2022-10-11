package com.codepath.apps.restclienttemplate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet
import kotlinx.parcelize.Parcelize

const val TWEET_EXTRA = "TWEET_EXTRA"
class TweetsAdapter(private val context: Context, private val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view, context)
    }

    // Populates data through holder
    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {

        val tweet: Tweet = tweets[position]

        holder.tvUserName.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body

        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.iv_profile_image)
        val tvUserName = itemView.findViewById<TextView>(R.id.tv_username)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tv_tweet_body)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val tweet = tweets[bindingAdapterPosition]

            Log.i("onClick", "Clicked on a tweet")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(TWEET_EXTRA, tweet)
            val pair1 = Pair.create<View?, String?>(ivProfileImage, "profile_image_transition")
            val pair2 = Pair.create<View?, String?>(tvUserName, "username_transition")
            val pair3 = Pair.create<View?, String?>(tvTweetBody, "tweet_body_transition")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, pair1, pair2, pair3)
            context.startActivity(intent, options.toBundle())
        }
    }
}