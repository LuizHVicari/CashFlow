package com.luizvicari.cashflow.entities

import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.luizvicari.cashflow.R
import com.luizvicari.cashflow.databinding.ActivityMainBinding

class SpinnerTypeActivity: Activity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val item = parent?.getItemAtPosition(pos).toString()
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val stringCreditArray = R.array.credit_details
        val stringDebitArray = R.array.debit_details

        var stringArray: Int? = null
        stringArray = if (item == "Credit") {
            stringCreditArray
        } else {
            stringDebitArray
        }

        ArrayAdapter.createFromResource(this, stringArray, android.R.layout.simple_spinner_item).also{
            adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spDetails.adapter = adapter
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}