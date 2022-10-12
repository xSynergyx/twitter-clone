package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvTweetBody: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivProfileImage = findViewById(R.id.iv_detail_image)
        tvUserName = findViewById(R.id.tv_detail_username)
        tvTweetBody = findViewById(R.id.tv_detail_tweet_body)

        val tweet = intent.getParcelableExtra<Tweet>(TWEET_EXTRA) as Tweet
        
        Glide.with(this).load(tweet.user?.publicImageUrl).into(ivProfileImage)
        tvUserName.text = tweet.user?.name
        tvTweetBody.text = tweet.body
    }
}