package com.wtech.budgetchecker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wtech.budgetchecker.databinding.TransactionLayoutBinding
import kotlin.math.abs

class TransactionAdapter
    (private  var transactions:List<Transaction>)
    : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.transaction_layout,parent,false)

        return  TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction=transactions[position]
        holder.label.text=transaction.label
        if (transaction.amount >=0){
            holder.amount.apply {
                text="+$%.2f".format(transaction.amount)
                setTextColor(ContextCompat.getColor(context,R.color.green))
            }

        } else{
            holder.amount.apply {
                text="-$%.2f".format(abs(transaction.amount))
                setTextColor(ContextCompat.getColor(context,R.color.red))
            }
        }

        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context,EditTransactionDetails::class.java)
            intent.putExtra("transaction",transaction)
            holder.itemView.context.startActivity(intent)
        }



    }

    override fun getItemCount(): Int=transactions.size
        class  TransactionViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            val binding=TransactionLayoutBinding.bind(itemView)
            val label=binding.label
            val amount=binding.amount


        }

   fun setTransactionData(transaction: List<Transaction>){
        this.transactions=transaction
        notifyDataSetChanged()
    }
}