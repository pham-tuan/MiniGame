package com.tuan.gamezone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.tuan.gamezone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpListener()
        setUpAnimation()
    }

    private fun setUpAnimation() {
        val animation = AnimationUtils.loadAnimation(this,R.anim.anim_button_play)
        Handler(Looper.getMainLooper()).postDelayed({binding?.play?.startAnimation(animation)
            binding?.title?.startAnimation(animation)},3000)
    }

    private fun setUpListener() {
        binding?.apply{
            play.setOnClickListener {
                val intent = Intent(this@MainActivity,PlayActivity::class.java)
                startActivity(intent)
                binding = null
            }

        }

    }

}