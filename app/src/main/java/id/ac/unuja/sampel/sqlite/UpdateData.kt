package id.ac.unuja.sampel.sqlite

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.ac.unuja.sampel.R
import id.ac.unuja.sampel.databinding.ActivityCreateDataBinding
import id.ac.unuja.sampel.databinding.ActivityUpdateDataBinding

class UpdateData : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateDataBinding
    private lateinit var db: DatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        noteId = intent.getIntExtra("note_id", -1)

        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getById(noteId)
        binding.etTitle.setText(note.title)
        binding.etDescription.setText(note.content)

        binding.btEdit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etDescription.text.toString()
            if (validateInput(title, content)) {
                val updateNote = Note(noteId, title, content)
                db.Update(updateNote)

                Toast.makeText(this, "Perubahan Berhasil", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validateInput(
        title: String,
        content: String,
    ): Boolean {
        return when {
            title.isEmpty() -> {
                binding.etTitle.error = "Title harus diisi"
                false
            }
            content.isEmpty() -> {
                binding.etDescription.error = "Description harus diisi"
                false
            }
            else -> true
        }
    }
}