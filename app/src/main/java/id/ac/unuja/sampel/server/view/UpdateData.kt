package id.ac.unuja.sampel.server.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import id.ac.unuja.sampel.databinding.ActivityUpdateDataBinding
import id.ac.unuja.sampel.server.api.NoteRequest
import id.ac.unuja.sampel.server.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateData : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDataBinding
    private val apiService by lazy { RetrofitFactory.createApiService(this) }
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) finish()

        loadNoteData()

        binding.btEdit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etDescription.text.toString()

            if (validateInput(title, content)) {
                updateNote(noteId, title, content)
            }
        }
    }
    private fun loadNoteData() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getNoteDetail(noteId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { note ->
                            binding.etTitle.setText(note.data?.title)
                            binding.etDescription.setText(note.data?.content)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateData, "Error loading note", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateNote(noteId: Int, title: String, content: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.updateNote(noteId, NoteRequest(title, content))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UpdateData, "Berhasil perbarui", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateData, "Update failed", Toast.LENGTH_SHORT).show()
                }
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