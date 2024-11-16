package com.example.quizonlineapp

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizonlineapp.databinding.ActivityQuizBinding
import com.example.quizonlineapp.databinding.ScoreDialogBinding
import np.com.bimalkafle.quizonline.QuestionModel

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    private lateinit var binding: ActivityQuizBinding
    private var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)
        }

        if (questionModelList.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "No questions available or time not set!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                Toast.makeText(this@QuizActivity, "Time's up!", Toast.LENGTH_LONG).show()
                finish() // Ends the quiz when the timer finishes
            }
        }.start() // Start the timer
    }

    private fun loadQuestions() {

        if(currentQuestionIndex == questionModelList.size){
            selectedAnswer = ""
            finishQuiz()
            return
        }
        if (questionModelList.isEmpty()) {
            Toast.makeText(this, "No questions to display!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        binding.apply {
            val currentQuestion = questionModelList[currentQuestionIndex]
            questionIndicatorTextview.text = "Question ${currentQuestionIndex + 1} / ${questionModelList.size}"
            questionProgressIndicator.progress =
                ((currentQuestionIndex.toFloat() / questionModelList.size.toFloat()) * 100).toInt()
            questionTextview.text = currentQuestion.question
            btn0.text = currentQuestion.options[0]
            btn1.text = currentQuestion.options[1]
            btn2.text = currentQuestion.options[2]
            btn3.text = currentQuestion.options[3]
        }
    }

    private fun handleAnswerSelection(selectedAnswer: String) {
        val correctAnswer = questionModelList[currentQuestionIndex].correct
        if (selectedAnswer == correctAnswer) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View) {
        // Reset all buttons to gray
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        if (clickedBtn.id == R.id.next_btn) {
            if(selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("Score", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        } else {
            // Highlight the selected option in orange
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishQuiz(){
        val totalQuestion = questionModelList.size
        val percentage = ((score.toFloat()/totalQuestion.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "${percentage}%"
            if(percentage>60){
                scoreTitle.text = "congrats you have pass"
                scoreTitle.setTextColor(Color.BLUE)

            }else{
                scoreTitle.text = "oops you have failed"
                scoreTitle.setTextColor(Color.RED)
            }

            scoreSubtitle.text = "$score out of $totalQuestion are correct"
            finishBtn.setOnClickListener(){
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()


    }
}
