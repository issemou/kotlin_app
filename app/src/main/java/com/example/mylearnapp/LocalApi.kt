package com.example.mylearnapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Context


class LocalApi(activity: AppCompatActivity) {

    private var sharedPreference = activity.getPreferences(Context.MODE_PRIVATE)

    fun updateBestScore(score:Int){
       val bestscore = getBestScore()
        if(score > bestscore){
            sharedPreference.edit().putInt(Best_Score_key, score).apply()
        }
    }

    fun getBestScore() = sharedPreference.getInt(Best_Score_key, 0)

    fun saveLatestScore(score:Int){
            sharedPreference.edit().putInt(Latest_Score_Key, score).apply()
    }
    fun getLatestScore() = sharedPreference.getInt(Latest_Score_Key, 0)

    fun putString(key: String, value :String?){
        sharedPreference.edit().putString(key, value).apply()
    }

    fun getString(key: String) = sharedPreference.getString(key, null)

    companion object{
        const val Best_Score_key = "BEST_SCORE"
        const val Latest_Score_Key = "LATEST_SCORE"
    }
}