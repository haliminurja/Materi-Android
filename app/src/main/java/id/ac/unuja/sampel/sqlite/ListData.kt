package id.ac.unuja.sampel.sqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unuja.sampel.R
import id.ac.unuja.sampel.databinding.ActivityListDataBinding

class ListData : AppCompatActivity() {

    private lateinit var binding : ActivityListDataBinding
    private lateinit var db: DatabaseHelper
    private lateinit var listAdapter: ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        listAdapter = ListAdapter(db.GetAll(), this)
        binding.listData.layoutManager = LinearLayoutManager(this)
        binding.listData.adapter = listAdapter


        binding.btTambahData.setOnClickListener{
            var intent = Intent(this, CreateData::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        listAdapter.refreshData(db.GetAll())
    }
}