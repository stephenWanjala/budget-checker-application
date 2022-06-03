package com.wtech.budgetchecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.wtech.budgetchecker.databinding.ActivityAddTransactionBinding


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
            if (amount==null){
                binding.AmountLayout.error="Please Enter A valid Amount"
            }
        }
        binding.cancelButton.setOnClickListener {
            finish()
        }


    }
}