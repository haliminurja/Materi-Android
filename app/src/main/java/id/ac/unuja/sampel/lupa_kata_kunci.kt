package id.ac.unuja.sampel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.ac.unuja.sampel.databinding.ActivityLupaKataKunciBinding
import java.util.regex.Pattern

class lupa_kata_kunci : AppCompatActivity() {
    private lateinit var binding: ActivityLupaKataKunciBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLupaKataKunciBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btResetKataKunci.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (isValidEmail(email)) {
                // Logika kirim email reset password
                Toast.makeText(this, "Instruksi reset password telah dikirim ke $email", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                binding.etEmail.error = "Email tidak valid"
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return Pattern.compile(emailPattern).matcher(email).matches()
    }
}