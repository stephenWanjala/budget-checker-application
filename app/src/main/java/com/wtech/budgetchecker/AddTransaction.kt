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

      binding.addTransaction.setOnClickListener {
          val amount: Double? = binding.editTextAmount.text.toString().toDoubleOrNull()
          val label: String = binding.editTextLabel.text.toString()
          val description: String = binding.editTextDescription.text.toString()

          if (amount ==null){
              binding.AmountLayout.error="Enter Amount"
          }  else if (label.isNullOrEmpty()){
              binding.labelLayout.error="Enter label"
          } else{
              val  transaction=Transaction(0,label, amount, description)
              insert(transaction)
          }
      }

        binding.editTextLabel.addTextChangedListener {
            if (it?.isNotEmpty() == true){
                binding.labelLayout.error=null
            }
        }
        binding.editTextAmount.addTextChangedListener {
            if (it?.isNotEmpty() == true){
                binding.AmountLayout.error=null
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