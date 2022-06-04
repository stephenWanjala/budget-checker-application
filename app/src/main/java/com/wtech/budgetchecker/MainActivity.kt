package com.wtech.budgetchecker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.wtech.budgetchecker.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transaction: Transaction
    private lateinit var deletedTransaction: Transaction
    private lateinit var transactionList:List<Transaction>
    private lateinit var oldTransactionList:List<Transaction>
private lateinit var binding: ActivityMainBinding
private lateinit var  myAdapter: TransactionAdapter
private lateinit var mLayoutManager: RecyclerView.LayoutManager

private lateinit var  dataBase: AppDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionList= arrayListOf()
        myAdapter= TransactionAdapter(transactionList)
        mLayoutManager=LinearLayoutManager(this@MainActivity)
        dataBase= Room.databaseBuilder(
            this@MainActivity,AppDataBase::class.java,"transactions"
        ).build()

        binding.recyclerView.apply {
            layoutManager=mLayoutManager
            adapter=myAdapter
        }
//        fetchAll()
//removed as on create call on resume
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,AddTransaction::class.java))
        }


//        swipeTo delete
        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
               return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactionList[viewHolder.adapterPosition])
            }

        }
//instanciate  the ontouchHelper

        val swipeHelper=ItemTouchHelper(itemTouchHelper)

//Attach to the  recycler view
        swipeHelper.attachToRecyclerView(binding.recyclerView)

    }


    private fun fetchAll(){
        GlobalScope.launch {

//            dataBase.transactionDao().insertAll(Transaction(0,"Ice cream",-80.00,"ate It"))
            transactionList=dataBase.transactionDao().getAll()

            runOnUiThread {
                updateUi()
                myAdapter.setTransactionData(transactionList)
            }
        }
    }






    private fun updateUi(){
        val totalAmount: Double = transactionList.sumOf { it.amount }
        val budget= transactionList.filter { it.amount > 0 }.sumOf { it.amount }
        val expense=totalAmount-budget

        binding.totalAmount.text = "$%.3f".format(totalAmount)
        binding.budgetAmount.text = "$%.2f".format(budget)
        binding.balanceAmount.text = "$%.2f".format(expense)
    }

    fun deleteTransaction(transaction: Transaction){
        deletedTransaction=transaction
        oldTransactionList=transactionList
        GlobalScope.launch {
            dataBase.transactionDao().delete(transaction)
            transactionList=transactionList.filter { transact ->
                transact.id!=transaction.id
            }
            runOnUiThread {
                updateUi()
            }
        }


    }
    override fun onRestart() {
        super.onRestart()

    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}