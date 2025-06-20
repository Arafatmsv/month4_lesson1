package com.ara.month4_lesson1.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.databinding.ItemAccountBinding

class AccountAdapter(
    val onEdit: (AccountModel) -> Unit,
    val onDelete: (String) -> Unit,
    val onStatusToogle: (String, Boolean) -> Unit
): RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private val items = mutableListOf<AccountModel>()

    fun setItems(accounts: List<AccountModel>) {
        items.clear()
        items.addAll(accounts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class AccountViewHolder(private val binding: ItemAccountBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(accountModel: AccountModel) = with(binding) {
            accountModel.apply {
                tvName.text = name
                tvBalance.text = balance.toString()
                tvCurrency.text = currency

                btnEdit.setOnClickListener {
                    onEdit(this)
                }

                btnDelete.setOnClickListener {
                    accountId?.let { onDelete(it) }
                }

                switchActive.apply {
                    setOnCheckedChangeListener(null)
                    isChecked = isActive
                    setOnCheckedChangeListener { buttonView, isChecked ->
                        accountId?.let { onStatusToogle(it,isChecked) }
                    }
                }
            }
        }
    }
}