package com.luizvicari.cashflow

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.luizvicari.cashflow.database.DatabaseLaunchHandler
import com.luizvicari.cashflow.database.DatabaseLaunchInterface
import com.luizvicari.cashflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var type: String? = null
    private var details: String? = null
    private lateinit var db: SQLiteDatabase
    private lateinit var dbInterface: DatabaseLaunchInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath("app.sqlite"),
            null
        )

        DatabaseLaunchHandler(this).writableDatabase
        dbInterface = DatabaseLaunchHandler(this)

        initTypeSpinner()
        initDetailSpinner()
        initButtons()

    }

    private fun btBalanceOnClick() {
        val balance = dbInterface.getBalance()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Your balance")
        builder.setMessage("Balance: $balance")
        builder.setNegativeButton("Close"){ _, _ ->
        }
        builder.show()
    }

    private fun btRegisterOnClick() {
        val value = binding.etValue.text.toString().toFloatOrNull()
        val date = binding.etDate.text.toString()

        if (value == null) {
            binding.etValue.requestFocus()
            Toast.makeText(this, R.string.value_focus, Toast.LENGTH_LONG).show()
        } else if (date.isEmpty()) {
            binding.etDate.requestFocus()
            Toast.makeText(this, R.string.date_focus, Toast.LENGTH_LONG).show()
        } else {
            dbInterface.insertRegistration(type!!, details!!, value, date)
        }
    }

    private fun initButtons() {
        binding.btList.setOnClickListener{
            val items = dbInterface.getAllItems()
            if (items.isEmpty()) {
                Toast.makeText(this, R.string.no_items, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btBalance.setOnClickListener{
            btBalanceOnClick()
        }

        binding.btRegister.setOnClickListener{
            btRegisterOnClick()
        }
    }

    private fun initTypeSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.types,
            android.R.layout.simple_spinner_item).also{
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spType.adapter = adapter
        }
        binding.spType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val item = parent?.getItemAtPosition(pos).toString()
                type = item

                val stringCreditArray = R.array.credit_details
                val stringDebitArray = R.array.debit_details
                val stringArray: Int = if (item == "Credit") {
                    stringCreditArray
                } else {
                    stringDebitArray
                }

                ArrayAdapter.createFromResource(parent!!.context, stringArray, android.R.layout.simple_spinner_item).also{
                        adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spDetails.adapter = adapter
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun initDetailSpinner() {
        binding.spDetails.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                details = parent?.getItemAtPosition(pos).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}