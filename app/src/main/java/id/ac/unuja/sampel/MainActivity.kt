package id.ac.unuja.sampel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import id.ac.unuja.sampel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            val namaPengguna = binding.etNamaPengguna.text.toString()
            val kataKunci = binding.etKataKunci.text.toString()
            if (validateInput(namaPengguna, kataKunci)) {
                val intent = Intent(this,menu::class.java)
                intent.putExtra("nama",namaPengguna)
                startActivity(intent)
                finish()
            }
        }

        binding.tvLupaKataKunci.setOnClickListener {
            startActivity(Intent(this, lupa_kata_kunci::class.java))
        }

        binding.tvDaftarAkun.setOnClickListener {
            startActivity(Intent(this, lupa_kata_kunci::class.java))
        }
    }

    private fun validateInput(namaPengguna: String, KataKunci: String): Boolean {
        return when {
            namaPengguna.isEmpty() -> {
                binding.etNamaPengguna.error = "Nama Pengguna tidak boleh kosong"
                false
            }
            KataKunci.isEmpty() -> {
                binding.etKataKunci.error = "Kata Kunci tidak boleh kosong"
                false
            }
            else -> true
        }
    }
}