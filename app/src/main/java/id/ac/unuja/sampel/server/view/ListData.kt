package id.ac.unuja.sampel.server.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unuja.sampel.databinding.ActivityListDataBinding
import id.ac.unuja.sampel.server.adapter.ResultAdapter
import id.ac.unuja.sampel.server.api.NoteResponse
import id.ac.unuja.sampel.server.api.NoteResponseList
import id.ac.unuja.sampel.server.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListData : AppCompatActivity() {
    private lateinit var binding: ActivityListDataBinding
    private lateinit var adapter: ResultAdapter
    private val apiService by lazy { RetrofitFactory.createApiService(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ResultAdapter(this, { action, note ->
            when (action) {
                is ResultAdapter.NoteAction.Delete -> deleteNote(note.id)
                is ResultAdapter.NoteAction.Update -> updateNote(note)
            }
        })

        binding.listData.apply {
            layoutManager = LinearLayoutManager(this@ListData)
            adapter = this@ListData.adapter
        }

        binding.btTambahData.setOnClickListener {
            startActivity(Intent(this, CreateData::class.java))
        }

        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getNotes()
                if (response.isSuccessful) {
                    val notes = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        adapter.updateNotes(notes)
                    }
                } else {
                    Toast.makeText(
                        this@ListData,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListData, "Error loading note", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteNote(noteId: Int) {
        lifecycleScope.launch {
            try {
                val response = apiService.deleteNote(noteId)
                if (response.isSuccessful && response.body()?.success == true) {
                    loadData() // Refresh data after delete
                    Toast.makeText(
                        this@ListData, "Berhasil Hapus", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ListData,
                        "Delete failed: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ListData, "Delete error: ${e.message}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateNote(note: NoteResponseList.Note) {
        Intent(this, UpdateData::class.java).apply {
            putExtra("note_id", note.id)
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData() // Refresh data when returning from other screens
    }
}