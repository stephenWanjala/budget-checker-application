package com.wtech.budgetchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.wtech.budgetchecker.databinding.ActivityEditTansactionDetailsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class EditTransactionDetails : AppCompatActivity() {

    private lateinit var  binding: ActivityEditTansactionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditTansactionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var transaction: Transaction =intent.getSerializableExtra("transaction") as Transaction

        binding.editTextLabel.setText(transaction.label)
        binding.editTextDescription.setText(transaction.description)
        binding.editTextAmount.setText("$ ${transaction.amount.toString()}")
        binding.updateTransaction.setOnClickListener {
            val amount: Double? = binding.editTextAmount.text.toString().toDoubleOrNull()
            val label: String = binding.editTextLabel.text.toString()
            val description: String = binding.editTextDescription.text.toString()

            if (amount ==null){
                binding.AmountLayout.error="Enter Amount"
            }  else if (label.isNullOrEmpty()){
                binding.labelLayout.error="Enter label"
            } else{
                val  transaction=Transaction(0,label, amount, description)
                update(transaction)
            }
        }

        binding.editTextLabel.addTextChangedListener {
            binding.matCardWithUpdateButton.visibility=View.VISIBLE
            if (it?.isNotEmpty() == true){
                binding.labelLayout.error=null
            }
        }
        binding.editTextAmount.addTextChangedListener {
            binding.matCardWithUpdateButton.visibility=View.VISIBLE
            if (it?.isNotEmpty() == true){
                binding.AmountLayout.error=null
            }
        }
        binding.editTextDescription.addTextChangedListener {
            binding.matCardWithUpdateButton.visibility=View.VISIBLE
        }
        binding.cancelButton.setOnClickListener {
            finish()
        }



    }
    private fun update(transaction: Transaction){
        val dataBase= Room.databaseBuilder(
            this@EditTransactionDetails,AppDataBase::class.java,"transactions"
        ).build()

        GlobalScope.launch {
            dataBase.transactionDao().update(transaction)
            finish()
        }
    }
}