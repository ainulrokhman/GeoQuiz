package com.denature.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prefImageButton: ImageButton
    private lateinit var nextImageButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(QuizViewModel::class.java)
    }
    private val KEY_INDEX = "index"
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prefImageButton = findViewById(R.id.pref_image_button)
        nextImageButton = findViewById(R.id.next_image_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        prefImageButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        nextImageButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.setCurrentIndex(currentIndex)

    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "fungsi onStart() dipanggil")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "fungsi onPause() dipanggil")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "fungsi onResume() dipanggil")
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "fungsi onStop() dipanggil")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "fungsi onDestroy() dipanggil")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.getIndex)
    }

}