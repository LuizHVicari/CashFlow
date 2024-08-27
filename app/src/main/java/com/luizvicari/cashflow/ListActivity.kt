package com.luizvicari.cashflow

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.luizvicari.cashflow.adapters.LaunchesListAdapter
import com.luizvicari.cashflow.database.DatabaseLaunchHandler
import com.luizvicari.cashflow.databinding.ActivityListBinding
import com.luizvicari.cashflow.databinding.ListItemBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath("app.sqlite"), null)
        val dbHandler = DatabaseLaunchHandler(this)
        val launches = dbHandler.getAllItems()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = LaunchesListAdapter(this, launches)
        binding.recyclerView.setHasFixedSize(true)
//        Toast.makeText(this, launches[0].type, Toast.LENGTH_LONG).show()
        println("teste")


    }
}