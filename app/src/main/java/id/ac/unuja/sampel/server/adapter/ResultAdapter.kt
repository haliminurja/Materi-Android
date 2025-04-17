package id.ac.unuja.sampel.server.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.ac.unuja.sampel.R
import id.ac.unuja.sampel.server.api.Note
import id.ac.unuja.sampel.server.api.NoteResponse

class ListAdapter (private var notes: List<NoteResponse>, context: Context) :

    RecyclerView.Adapter<ListAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updatButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener{

        }
        holder.deleteButton.setOnClickListener{

        }
    }

    fun refreshData(newNotes: List<NoteResponse>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}