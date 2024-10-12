package com.example.lab_exam_4

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_exam_4.databinding.ViewBinding

import java.util.Locale

//import kotlinx.android.synthetic.main.view.view.priority
//import kotlinx.android.synthetic.main.view.view.title

class Adapter(private val data: List<CardInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val description = binding.description
        val priority = binding.priority
        val layout = binding.mylayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        val context = holder.itemView.context

        when (currentItem.priority.lowercase(Locale.ROOT)) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#FF0000"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#FFBE00"))
            "low" -> holder.layout.setBackgroundColor(Color.parseColor("#008F11"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#0000FF"))
        }

        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.priority.text = currentItem.priority

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateCard::class.java)
            intent.putExtra("id", position)
            context.startActivity(intent)
        }
    }
}
