package com.example.lab_exam_4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _taskList = MutableLiveData<List<CardInfo>>()
    val taskList: LiveData<List<CardInfo>> = _taskList
    private lateinit var database: myDatabase

    fun initDatabase(applicationContext: android.content.Context) {
        database = Room.databaseBuilder(
            applicationContext,
            myDatabase::class.java, "To_Do"
        ).build()

        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _taskList.postValue(database.dao().getTasks())
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            database.dao().deleteAll()
            _taskList.postValue(emptyList())
        }
    }

    fun addTask(title: String, description: String, priority: String) {
        viewModelScope.launch {
            val newTask = Entity(0, title, description, priority)
            database.dao().insertTask(newTask)
            loadTasks()
        }
    }
}
