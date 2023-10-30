package com.tuan.gamezone

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tuan.gamezone.customView.start
import com.tuan.gamezone.databinding.ActivityPlayBinding
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayBinding
    private var lisQuestion: List<Questions>? = null
    private var currentQuestion: Questions? = null
    var countDownTimer: CountDownTimer? = null
    private var currentText = ""
    var count  = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDataQuestion()
        handleRandom()
        setUpListener()
        handleCountDown()
    }

    private fun handleCountDown() {
        binding.tvCountDown.start {
            if (binding.tvCountDown.endValue == 0) {
                binding.tvCountDown.visibility = View.GONE
                binding.viewCountDown.visibility = View.GONE
                binding.tvGo.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.tvGo.visibility = View.GONE
                    binding.viewCountDown.visibility = View.GONE
                },600)
            }
        }
    }


    private fun handlerTimeUp() {
        animationTimeUp()
    }

    private fun setUpListener() {
        val onClick = View.OnClickListener {
            setValue(it)
        }
        binding.tvZero.setOnClickListener(onClick)
        binding.tvOne.setOnClickListener(onClick)
        binding.tvTwo.setOnClickListener(onClick)
        binding.tvThree.setOnClickListener(onClick)
        binding.tvFour.setOnClickListener(onClick)
        binding.tvFive.setOnClickListener(onClick)
        binding.tvSix.setOnClickListener(onClick)
        binding.tvSeven.setOnClickListener(onClick)
        binding.tvEight.setOnClickListener(onClick)
        binding.tvNine.setOnClickListener(onClick)
    }
    private fun animationCalculateZoomIn() {
        val animation = AnimationUtils.loadAnimation(this,R.anim.anim_zoom_in)
        binding.operator.startAnimation(animation)
    }

    private fun animationCalculateZoomOut() {
        val animation = AnimationUtils.loadAnimation(this,R.anim.anim_zoom_out)
        binding.operator.startAnimation(animation)
    }
    private fun animationTimeUp() {
        val animation = AnimationUtils.loadAnimation(this,R.anim.anim_time_up)
        binding.ivTimeUp.startAnimation(animation)
        binding.ivTimeUp.visibility = View.VISIBLE

        binding.llNumber.alpha = 0.5F
        binding.tvZero.isEnabled = false
        binding.tvOne.isEnabled = false
        binding.tvTwo.isEnabled = false
        binding.tvThree.isEnabled = false
        binding.tvFour.isEnabled = false
        binding.tvFive.isEnabled = false
        binding.tvSix.isEnabled = false
        binding.tvSeven.isEnabled = false
        binding.tvEight.isEnabled = false
        binding.tvNine.isEnabled = false
    }
    private fun handleRandom() {
        animationCalculateZoomOut()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivRight.visibility = View.GONE
        },500)
        val itemQuestion = Random.nextInt(lisQuestion?.size ?: 0)
        currentQuestion = lisQuestion?.get(itemQuestion)
        binding.tvnumberA.text = currentQuestion?.soA ?: ""
        binding.tvnumberB.text = currentQuestion?.soB ?: ""
        binding.tvnumberResult.text = currentQuestion?.result ?: ""
        binding.numberOperator.text = currentQuestion?.operator ?: ""
        handleBackground(currentQuestion)
    }

    private fun handleBackground(question: Questions?) {
        if (question?.soA.equals("?")){
            binding.tvnumberA.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_answer)
        } else {
            binding.tvnumberA.background = null
        }

        // check data numberTwo

        if (question?.soB.equals("?")){
            binding.tvnumberB.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_answer)
        } else {
            binding.tvnumberB.background = null
        }

        if (question?.result.equals("?")){
            binding.tvnumberResult.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_answer)
        } else {
            binding.tvnumberResult.background = null
        }
        binding.tvAgain.setOnClickListener {
            val intent = Intent(this@PlayActivity, PlayActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setUpDataQuestion() {
        lisQuestion = listOf(
            Questions("?", "8", "+", "20", "12"),
            Questions("10", "8", "-", "?", "2"),
            Questions("?", "3", "x", "12", "4"),
            Questions("8", "?", "+", "24", "16"),
            Questions("12", "?", "-", "5", "7"),
        )
    }

    private fun setValue(view : View){
        binding.apply {
            if (currentQuestion?.soA.equals("?")) {
                handleViewNumberOne(view)
            }
            if (currentQuestion?.soB.equals("?")) {
                handleViewNumberTwo(view)
            }
            if (currentQuestion?.result.equals("?")) {
                handleViewNumberResult(view)
            }

        }
    }

    private fun handleViewNumberResult(view: View) {
        currentText =  binding.tvnumberResult.text.toString()
        if (currentText == "?") {
            binding.tvnumberResult.text = (view as Button).text.toString()
        }
        else {
            binding.tvnumberResult.text = currentText + (view as Button).text.toString()
        }
        checkLengthValue(TYPE_RESULT)
    }

    private fun handleViewNumberTwo( view: View) {
        currentText =  binding.tvnumberB.text.toString()
        if (currentText == "?") {
            binding.tvnumberB.text = (view as Button).text.toString()
        }
        else {
            binding.tvnumberB.text = currentText + (view as Button).text.toString()
        }
        checkLengthValue(TYPE_NUMBER_B)
    }

    private fun handleViewNumberOne( view: View) {
        currentText =  binding.tvnumberA.text.toString()
        if (currentText == "?") {
            binding.tvnumberA.text = (view as Button).text.toString()
        }
        else {
            binding.tvnumberA.text = currentText + (view as Button).text.toString()
        }
        checkLengthValue(TYPE_NUMBER_A)
    }

    private fun checkLengthValue(typeQuestion: Int) {
        when(typeQuestion) {
            TYPE_NUMBER_A -> {
                if ((currentQuestion?.value?.length ?: 0) == (binding.tvnumberA.length())){
                    handleDataSuccess(TYPE_NUMBER_A)
                }
            }
            TYPE_NUMBER_B -> {
                if ((currentQuestion?.value?.length ?: 0) == (binding.tvnumberB.length())){
                    handleDataSuccess(TYPE_NUMBER_B)
                }
            }
            TYPE_RESULT -> {
                if ((currentQuestion?.value?.length ?: 0) == (binding.tvnumberResult.length())){
                    handleDataSuccess(TYPE_RESULT)
                }
            }
        }
    }

    private fun handleDataSuccess(typeQuestion: Int) {
        when (typeQuestion) {
            TYPE_NUMBER_A -> handleDataSuccessWithNumber(binding.tvnumberA)

            TYPE_NUMBER_B -> handleDataSuccessWithNumber(binding.tvnumberB)

            TYPE_RESULT -> handleDataSuccessWithNumber(binding.tvnumberResult)

            else -> handlerTimeUp()
        }
    }

    private fun handleDataSuccessWithNumber(tvNumber: TextView) {
        if (tvNumber.text == currentQuestion?.value) {
            binding.ivRight.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                binding.ivRight.visibility = View.GONE
                handleRandom()
            }, 600)
            binding.tvCount.text = (++count).toString()
            animationCalculateZoomIn()
        } else {
            binding.ivRight.visibility = View.GONE
            binding.ivWrong.visibility = View.VISIBLE
            val intent = Intent(this@PlayActivity, GameOver_Main::class.java)
            startActivity(intent)
            intentOver()
        }
    }

    private fun intentOver() {
        val intent = Intent(this, GameOver_Main::class.java)
        intent.putExtra("count", count)
        startActivity(intent)
    }


    companion object {
        const val TYPE_NUMBER_A = 0
        const val TYPE_NUMBER_B = 1
        const val TYPE_RESULT = 2
    }

}