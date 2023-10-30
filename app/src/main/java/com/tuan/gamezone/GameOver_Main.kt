package com.tuan.gamezone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.tuan.gamezone.databinding.ActivityGameOverMainBinding

class GameOver_Main : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityGameOverMainBinding.inflate(layoutInflater)
        initview()
        setContentView(binding.root)
    }

    private fun initview() {
        sharedPreferences = getSharedPreferences("myBestScore", Context.MODE_PRIVATE)
        //Event Intent

        binding.btnReplayUp.setOnClickListener {
            startActivity(Intent(this, PlayActivity::class.java))
        }

        binding.llHome.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        val score = intent.getIntExtra("count",0)

        restoreScore()

        binding.tvScore.text = score.toString()
        if(binding.tvBestScore.text.toString().toInt() < binding.tvScore.text.toString().toInt()){
            binding.tvBestScore.text = binding.tvScore.text
            saveScore(score)
        }
    }


    private fun saveScore(score:Int) {
        // Lưu dữ liệu score vào SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putInt("count", score)
        editor.apply()

    }

    private fun restoreScore() {
        // Khôi phục dữ liệu score từ SharedPreferences
        val bestScore = sharedPreferences.getInt("count", 0)

        // Hiển thị dữ liệu score lên tvBestScore
        binding.tvBestScore.text = bestScore.toString()
    }

}