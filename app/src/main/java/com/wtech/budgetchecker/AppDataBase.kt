package com.wtech.budgetchecker

import androidx.room.Database
import androidx.room.RoomDatabase


//arrayOf(Transaction::class)
@Database(entities = [Transaction::class], version = 1)
abstract class AppDataBase :RoomDatabase(){
    abstract fun transactionDao():TransactionDao

}