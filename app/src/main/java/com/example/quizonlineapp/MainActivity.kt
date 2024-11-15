package com.example.quizonlineapp

import QuizModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonlineapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declare the binding object
    private lateinit var binding: ActivityMainBinding
    lateinit var quizModelList : MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view to the root of the binding
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView(){
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){
        quizModelList.add(QuizModel("1","programming","all the programing","10"))
        quizModelList.add(QuizModel("2","programming1","all the programing","15"))
        quizModelList.add(QuizModel("3","programming2","all the programing","50"))
        setupRecyclerView()
    }
}
