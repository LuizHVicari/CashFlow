package com.luizvicari.cashflow

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.luizvicari.cashflow.database.DatabaseLaunchHandler
import com.luizvicari.cashflow.database.DatabaseLaunchInterface
import com.luizvicari.cashflow.databinding.ActivityMainBinding
import java.time.LocalDate
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var type: String? = null
    private var details: String? = null
    private lateinit var db: SQLiteDatabase
    private lateinit var dbInterface: DatabaseLaunchInterface
    private var dateSelected = false

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
        builder.setTitle(getString(R.string.your_balance))
        builder.setMessage(getString(R.string.show_balance, balance))
        builder.setNegativeButton(getString(R.string.close)){ _, _ ->
        }
        builder.show()
    }

    private fun btRegisterOnClick() {
        val value = binding.etValue.text.toString().toFloatOrNull()
        val date = binding.tvSelectDate.text.toString()

        if (value == null) {
            binding.etValue.requestFocus()
            Toast.makeText(this, R.string.value_focus, Toast.LENGTH_LONG).show()
        } else if (!dateSelected) {
            val errorColor = "#DA342E"
            binding.tvSelectDate.setTextColor(Color.parseColor(errorColor))
            Toast.makeText(this, R.string.date_focus, Toast.LENGTH_LONG).show()
        } else {
            dbInterface.insertRegistration(type!!, details!!, value, date)
            Toast.makeText(this, R.string.resgistered, Toast.LENGTH_LONG).show()
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

        binding.tvSelectDate.setOnClickListener{
            DatePickerDialog(this, DateSelectListener(binding), LocalDate.now().year, LocalDate.now().monthValue.toInt(), LocalDate.now().dayOfMonth.toInt()).show()
            dateSelected = true
        }
    }

    class DateSelectListener(private val binding: ActivityMainBinding): DatePickerDialog.OnDateSetListener {
        override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
            if (Locale.getDefault() == Locale.US){
                binding.tvSelectDate.text = "$year / $month / $day"
            } else {
                binding.tvSelectDate.text = "$day / $month / $year"
            }
            val primaryColor = "#824C76"
            binding.tvSelectDate.setTextColor(Color.parseColor(primaryColor))
        }

    }

    private fun initTypeSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.types,
            android.R.layout.simple_spinner_item).also{
                adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_list)
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
                    adapter.setDropDownViewResource(R.layout.spinner_list)
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