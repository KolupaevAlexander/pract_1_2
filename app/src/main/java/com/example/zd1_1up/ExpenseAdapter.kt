package com.example.zd1_1up

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ExpenseAdapter(private val expenseList: List<ExpenseItem>,
                     private val clickListener: Fragment1
) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_disain, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {

        val expense = expenseList[position]
        holder.bind(expense)

        // Обработка нажатия на элемент списка
        holder.itemView.setOnClickListener {
            clickListener.onExpenseItemClick(expense)
        }
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)

        fun bind(expense: ExpenseItem) {
            nameTextView.text = expense.name
            amountTextView.text = "${expense.amount} рублей"
        }
    }
}
