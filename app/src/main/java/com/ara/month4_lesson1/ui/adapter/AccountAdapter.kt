package com.ara.month4_lesson1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ara.month4_lesson1.R
import com.ara.month4_lesson1.data.model.AccountModel

class AccountAdapter: RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private val items = mutableListOf<AccountModel>()

    fun setItems(accounts: List<AccountModel>) {
        items.clear()
        items.addAll(accounts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class AccountViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(accountModel: AccountModel) = with(itemView) {
            findViewById<TextView>(R.id.tv_name).text = accountModel.name
            findViewById<TextView>(R.id.tv_balance).text = accountModel.balance.toString()
            findViewById<TextView>(R.id.tv_currency).text = accountModel.currency
        }
    }
}