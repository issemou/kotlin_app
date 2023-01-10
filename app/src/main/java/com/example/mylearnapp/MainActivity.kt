package com.example.mylearnapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()  {

    private lateinit var localApi: LocalApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localApi = LocalApi(this)
        updateScore()

        var btn = findViewById<Button>(R.id.startButton)
        btn.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(GameActivity.LEVEL_TAG, (0..3).random())
            startActivityForResult(intent, RESULT_CODE_GAME)
        }
    }

    private fun updateScore() {
        findViewById<TextView>(R.id.mainLastScore).text =
            "Best Score = ${localApi.getLatestScore().toString()}"
        findViewById<TextView>(R.id.mainBestScore).text =
            "Latest Score = ${localApi.getBestScore().toString()}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESULT_CODE_GAME){
            if(resultCode == RESULT_OK){
               val res=  data?.getIntExtra(GameActivity.RESULTAT_TAG, 0)?: 0
                localApi.updateBestScore(res)
                localApi.saveLatestScore(res)
                updateScore()
            }
        }
    }

    companion object{
        const val RESULT_CODE_GAME = 111
    }
}