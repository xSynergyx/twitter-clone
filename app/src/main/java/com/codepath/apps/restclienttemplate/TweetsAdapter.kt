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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.codepath.apps.restclienttemplate.models.Tweet
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.toDuration

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

        val createdAt = tweet.createdAt
        val elapsedTime = timeSinceTweet(createdAt)

        holder.tvUserName.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body
        holder.tvTimestamp.text = elapsedTime

        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).transform(RoundedCorners(100)).into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: ArrayList<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.iv_profile_image)
        val tvUserName = itemView.findViewById<TextView>(R.id.tv_username)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tv_tweet_body)
        val tvTimestamp = itemView.findViewById<TextView>(R.id.tv_timestamp)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.i("onClick", "Clicked on a tweet")
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

    @OptIn(ExperimentalTime::class)
    fun timeSinceTweet(createdAt: String): String {

        // Get the created at value for the tweet and parse the string to a Date object
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy")
        val parsedDate: Date = dateFormat.parse(createdAt)

        // Get the current time and parse that to a Date as well
        val currentDate = LocalDateTime.now(ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss +0000 yyyy")
        val formatted = currentDate.format(formatter)
        val parsedCurrentDate: Date = dateFormat.parse(formatted.toString())

        // Find the time difference between the two dates
        val diff: Long = parsedCurrentDate.time - parsedDate.time

        // Convert the returned long into time in seconds
        val seconds = diff.toDuration(TimeUnit.MILLISECONDS).toInt(TimeUnit.SECONDS)

        var tweetTime = ""
        tweetTime = if (seconds>=86400) {
            val time: Int = seconds / 86400
            "$time" + "d"
        } else if (seconds>=3600) {
            val time: Int = seconds / 3600
            "$time" + "h"
        } else if (seconds >= 60){
            val time: Int = seconds / 60
            "$time" + "m"
        } else {
            val time: Int = seconds
            "$time" + "s"
        }

        return tweetTime
    }
}