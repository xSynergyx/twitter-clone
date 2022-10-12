package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class User(

    var name: String = "",
    var screenName: String = "",
    var publicImageUrl: String = "",
) : Parcelable {

    // Something we can reference without creating a new instance of this user object
    companion object {

        // Going to convert this json object into a user
        fun fromJson(jsonObject: JSONObject): User {
            val user = User()
            user.name = jsonObject.getString("name")
            user.screenName = jsonObject.getString("screen_name")
            user.publicImageUrl = jsonObject.getString("profile_image_url_https")

            return user
        }
    }
}