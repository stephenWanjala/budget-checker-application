package com.wtech.budgetchecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.wtech.budgetchecker.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddTransaction : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amount: Double? = binding.editTextAmount.text.toString().toDoubleOrNull()
        val label: String = binding.editTextLabel.text.toString()
        val description: String = binding.editTextDescription.text.toString()

        binding.editTextLabel.addTextChangedListener {
            if (it!!.count() >0){
                binding.labelLayout.error=null
            }
        }
        binding.editTextAmount.addTextChangedListener {
            if (it!!.isNotEmpty()){
                binding.AmountLayout.error=null
            }
        }

        binding.addTransaction.setOnClickListener {
            if(label.isEmpty()){
                binding.labelLayout.error="Please Enter a valid Label"
            }
             else if (amount==null){
                binding.AmountLayout.error="Please Enter A valid Amount"
            }
            else{
//                take values and save to db
                val transaction=Transaction(0,label, amount, description)
                insert(transaction)
            }
        }
        binding.cancelButton.setOnClickListener {
            finish()
        }



    }
        private fun insert(transaction: Transaction){
            val dataBase= Room.databaseBuilder(
                this@AddTransaction,AppDataBase::class.java,"transactions"
            ).build()

            GlobalScope.launch {
                 dataBase.transactionDao().insertAll(transaction)
                finish()
            }
        }
}