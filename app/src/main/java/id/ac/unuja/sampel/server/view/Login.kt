package id.ac.unuja.sampel.server.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import id.ac.unuja.sampel.R
import id.ac.unuja.sampel.databinding.ActivityCreateDataBinding
import id.ac.unuja.sampel.databinding.ActivityLoginBinding
import id.ac.unuja.sampel.menu
import id.ac.unuja.sampel.server.api.LoginRequest
import id.ac.unuja.sampel.server.api.NoteRequest
import id.ac.unuja.sampel.server.api.RetrofitFactory
import id.ac.unuja.sampel.server.api.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val apiService by lazy { RetrofitFactory.createApiService(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            val namaPengguna = binding.etNamaPengguna.text.toString()
            val kataKunci = binding.etKataKunci.text.toString()
            if (validateInput(namaPengguna, kataKunci)) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val response = apiService.login(LoginRequest(namaPengguna, kataKunci))
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                response.body()?.let { loginResponse ->
                                    if (loginResponse.success) {
                                        loginResponse.data?.let { tokenData ->
                                            TokenManager.saveToken(this@Login, tokenData.token)
                                            val intent = Intent(this@Login, ListData::class.java).apply {
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            }
                                            startActivity(intent)
                                            finish()
                                        }
                                    } else {
                                        Toast.makeText(this@Login, "Login gagal", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this@Login, "Login gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
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