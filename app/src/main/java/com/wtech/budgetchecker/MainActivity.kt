package com.wtech.budgetchecker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.wtech.budgetchecker.databinding.ActivityMainBinding
import com.wtech.budgetchecker.databinding.TransactionLayoutBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.exp

class MainActivity : AppCompatActivity() {
private lateinit var transaction:List<Transaction>
private lateinit var binding: ActivityMainBinding
private lateinit var  myAdapter: TransactionAdapter
private lateinit var mLayoutManager: RecyclerView.LayoutManager

private lateinit var  dataBase: AppDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transaction= arrayListOf()
        myAdapter= TransactionAdapter(transaction)
        mLayoutManager=LinearLayoutManager(this@MainActivity)
        dataBase= Room.databaseBuilder(
            this@MainActivity,AppDataBase::class.java,"transactions"
        ).build()

        binding.recyclerView.apply {
            layoutManager=mLayoutManager
            adapter=myAdapter
        }
//        fetchAll()
//removed as oncreate call on resume
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,AddTransaction::class.java))
        }




    }


    private fun fetchAll(){
        GlobalScope.launch {

            transaction=dataBase.transactionDao().getAll()

            runOnUiThread {
                updateUi()
                myAdapter.setTransactionData(transaction)
            }
        }
    }

    private fun updateUi(){
        val totalAmount: Double = transaction.sumOf { it.amount }
        val budget= transaction.filter { it.amount > 0 }.sumOf { it.amount }
        val expense=totalAmount-budget

        binding.totalAmount.text = "$%.3f".format(totalAmount)
        binding.budgetAmount.text = "$%.2f".format(budget)
        binding.balanceAmount.text = "$%.2f".format(expense)
    }

    override fun onRestart() {
        super.onRestart()

    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}