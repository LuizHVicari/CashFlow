package com.luizvicari.cashflow.database

import com.luizvicari.cashflow.entities.Launch

interface DatabaseLaunchInterface {

    fun insertRegistration(type: String, details: String, value: Float, date: String): Boolean

    fun getBalance(): Float

    fun getAllItems(): List<Launch>
}