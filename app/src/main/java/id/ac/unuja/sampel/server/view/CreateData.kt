package id.ac.unuja.sampel.server.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import id.ac.unuja.sampel.R
import id.ac.unuja.sampel.databinding.ActivityCreateDataBinding
import id.ac.unuja.sampel.databinding.ActivityListDataBinding
import id.ac.unuja.sampel.server.api.NoteRequest
import id.ac.unuja.sampel.server.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateData : AppCompatActivity() {
    private lateinit var binding: ActivityCreateDataBinding
    private val apiService by lazy { RetrofitFactory.createApiService(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSimpan.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etDescription.text.toString()
            if (validateInput(title, content)) {
                createNote(title, content)
            }
        }
    }
    private fun createNote(title: String, content: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.createNote(NoteRequest(title, content))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CreateData, "Berhasil Simpan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@CreateData, "Gagal Simpan", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CreateData, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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