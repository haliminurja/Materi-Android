package id.ac.unuja.sampel.server.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.ac.unuja.sampel.databinding.ListItemBinding
import id.ac.unuja.sampel.server.api.NoteResponseList

class ResultAdapter(
    private val context: Context,
    private val onNoteAction: (NoteAction, NoteResponseList.Note) -> Unit
) : ListAdapter<NoteResponseList.Note, ResultAdapter.NoteViewHolder>(NoteDiffCallback()) {

    sealed class NoteAction {
        object Update : NoteAction()
        object Delete : NoteAction()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteResponseList.Note) {
            binding.apply {
                titleTextView.text = note.title
                contentTextView.text = note.content

                updateButton.setOnClickListener {
                    onNoteAction(NoteAction.Update, note)
                }

                deleteButton.setOnClickListener {
                    onNoteAction(NoteAction.Delete, note)
                }
            }
        }
    }

    private class NoteDiffCallback : DiffUtil.ItemCallback<NoteResponseList.Note>() {
        override fun areItemsTheSame(
            oldItem: NoteResponseList.Note,
            newItem: NoteResponseList.Note
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NoteResponseList.Note,
            newItem: NoteResponseList.Note
        ): Boolean = oldItem == newItem
    }

    fun updateNotes(notes: List<NoteResponseList.Note>) {
        submitList(notes.toMutableList())
    }
}
