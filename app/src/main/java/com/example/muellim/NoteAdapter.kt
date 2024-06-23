package com.example.muellim

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muellim.Details
import com.example.muellim.databinding.RecyclerRowBinding


class NoteAdapter(val noteList: ArrayList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    class NoteHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binding.title.text = noteList.get(position).title
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Details::class.java)
            intent.putExtra("id", noteList.get(position).id.toString())
            intent.putExtra("info", "old")
            holder.itemView.context.startActivity(intent)
        }
    }
}