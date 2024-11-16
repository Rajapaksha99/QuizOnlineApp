package com.example.quizonlineapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonlineapp.databinding.ActivityMainBinding
import np.com.bimalkafle.quizonline.QuestionModel
import np.com.bimalkafle.quizonline.QuizModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var quizModelList: MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView() {
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {

        val listQuestionModel = mutableListOf<QuestionModel>()
        listQuestionModel.add(QuestionModel("WHAT IS ANDROID", mutableListOf("langauge","os","er","none"),"os"))
        listQuestionModel.add(QuestionModel("WHAT IS abc", mutableListOf("1","2","3","none"),"2"))
        listQuestionModel.add(QuestionModel("WHAT IS xyz", mutableListOf("4","5","6","none"),"2"))


        quizModelList.add(QuizModel("1", "Programming Basics","Learn all about programming", "10", listQuestionModel
            )
        )
        /*quizModelList.add(
            QuizModel(
                id = "2",
                title = "Advanced Programming",
                subtitle = "Deep dive into programming",
                time = "15",
                questionList = listOf()
            )
        )
        quizModelList.add(
            QuizModel(
                id = "3",
                title = "Expert Programming",
                subtitle = "Master programming techniques",
                time = "50",
                questionList = listOf()
            )
        )

         */
        setupRecyclerView()
    }
}
