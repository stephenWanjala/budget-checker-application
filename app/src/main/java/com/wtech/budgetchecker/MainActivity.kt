package com.wtech.budgetchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wtech.budgetchecker.databinding.ActivityMainBinding
import com.wtech.budgetchecker.databinding.TransactionLayoutBinding
import kotlin.math.exp

class MainActivity : AppCompatActivity() {
private lateinit var transaction:ArrayList<Transaction>
private lateinit var binding: ActivityMainBinding
private lateinit var  myAdapter: TransactionAdapter
private lateinit var mLayoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transaction= arrayListOf(
            Transaction("weekend",905.89),
            Transaction("Breakfast",-90.8),
            Transaction("Gasoline",95.9),
            Transaction("Grasp",45.89),
            Transaction("Oranges",-45.89),
            Transaction("Banana",45.80),
            Transaction("Wash Wash",-75.00),
            Transaction("water",-2.00),
            Transaction("Car park",-700.78)
        )
        myAdapter= TransactionAdapter(transaction)
        mLayoutManager=LinearLayoutManager(this@MainActivity)
        binding.recyclerView.apply {
            layoutManager=mLayoutManager
            adapter=myAdapter
        }
        updateUi()





    }

    private fun updateUi(){
        val totalAmount: Double = transaction.sumOf { it.amount }
        val budget= transaction.filter { it.amount > 0 }.sumOf { it.amount }
        val expense=totalAmount-budget

        binding.totalAmount.text = "$%.3f".format(totalAmount)
        binding.budgetAmount.text = "$%.2f".format(budget)
        binding.balanceAmount.text = "$%.2f".format(expense)
    }
}