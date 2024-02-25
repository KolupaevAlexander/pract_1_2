package com.example.prakt1_2_v1

import android.provider.BaseColumns

object DBContract {

    class ConsumptionEntry: BaseColumns
    {
        companion object{
            val TABLE_NAME = "consumption_database"
            val NAME = "name"
            val CATEGORY = "category"
            val DATE = "date"
            val AMOUNT = "amount"
        }
    }
}