package com.luizvicari.cashflow

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
import com.luizvicari.cashflow.adapters.LaunchesListAdapter
import com.luizvicari.cashflow.database.DatabaseLaunchHandler
import com.luizvicari.cashflow.databinding.ActivityListBinding


class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val db = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath("app.sqlite"), null)
        val dbHandler = DatabaseLaunchHandler(this)
        val launches = dbHandler.getAllItems()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = LaunchesListAdapter(this, launches)
        binding.recyclerView.setHasFixedSize(true)

    }


}