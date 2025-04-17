package id.ac.unuja.sampel

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.ac.unuja.sampel.databinding.ActivityMenuBinding
import id.ac.unuja.sampel.server.view.Login
import id.ac.unuja.sampel.sqlite.ListData

class menu : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        binding.tvNamaPengguna.text = sessionManager.getData()[SessionManager.KEY_USERNAME]

        binding.btSqlite.setOnClickListener {
            startActivity(Intent(this, ListData::class.java))
        }
        binding.btRestApi.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        binding.btLogout.setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}