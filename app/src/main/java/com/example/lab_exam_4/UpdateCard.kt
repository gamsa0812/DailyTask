package com.example.lab_exam_4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lab_exam_4.databinding.ActivityUpdateCardBinding


import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCardBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,myDatabase::class.java,"To_Do"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val description = DataObject.getData(pos).description
            val priority = DataObject.getData(pos).priority
            binding.createTitle.setText(title)
            binding.createDescription.setText(description)
            binding.createPriority.setText(priority)

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(pos+1,
                            binding.createTitle.text.toString(),
                            binding.createDescription.text.toString(),
                            binding.createPriority.text.toString())
                    )
                }
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                DataObject.updateData(
                    pos,
                    binding.createTitle.text.toString(),
                    binding.createDescription.text.toString(),
                    binding.createPriority.text.toString()


                )
                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(pos+1,binding.createTitle.text.toString(),
                            binding.createDescription.text.toString(),
                            binding.createPriority.text.toString())
                    )

                }

                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
