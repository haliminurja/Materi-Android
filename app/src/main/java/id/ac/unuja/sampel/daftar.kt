package id.ac.unuja.sampel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.ac.unuja.sampel.databinding.ActivityDaftarBinding
import java.util.regex.Pattern

class daftar : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btDaftar.setOnClickListener {
            val namaPengguna = binding.etNamaPengguna.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etKataKunci.text.toString()
            val confirmPassword = binding.etKataKunciKonfirmasi.text.toString()
            if (validateInput(namaPengguna, email, password, confirmPassword)) {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, menu::class.java))
                finish()
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun validateInput(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return when {
            fullName.isEmpty() -> {
                binding.etNamaPengguna.error = "Nama lengkap harus diisi"
                false
            }
            !isValidEmail(email) -> {
                binding.etEmail.error = "Email tidak valid"
                false
            }
            password.length < 8 -> {
                binding.etKataKunci.error = "Password minimal 8 karakter"
                false
            }
            !isValidPassword(password) -> {
                binding.etKataKunci.error = "Harus mengandung huruf besar, angka, dan simbol"
                false
            }
            password != confirmPassword -> {
                binding.etKataKunciKonfirmasi.error = "Password tidak cocok"
                false
            }
            !binding.ckKonfrimasi.isChecked -> {
                Toast.makeText(this, "Anda harus menyetujui syarat dan ketentuan", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return Pattern.compile(emailPattern).matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$"
        return Pattern.compile(passwordPattern).matcher(password).matches()
    }
}