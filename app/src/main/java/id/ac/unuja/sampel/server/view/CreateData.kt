package id.ac.unuja.sampel.sqlite

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.ac.unuja.sampel.R
import id.ac.unuja.sampel.databinding.ActivityCreateDataBinding
import id.ac.unuja.sampel.databinding.ActivityListDataBinding

class CreateData : AppCompatActivity() {
    private lateinit var binding : ActivityCreateDataBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.btSimpan.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etDescription.text.toString()
            if (validateInput(title, content)) {
                val note = Note(0, title, content)
                db.Save(note)
                Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
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