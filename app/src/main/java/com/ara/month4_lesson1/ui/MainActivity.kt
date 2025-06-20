package com.ara.month4_lesson1.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.ara.month4_lesson1.R
import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.databinding.ActivityMainBinding
import com.ara.month4_lesson1.databinding.ItemDialogBinding
import com.ara.month4_lesson1.presenter.AccountContract
import com.ara.month4_lesson1.presenter.AccountPresenter
import com.ara.month4_lesson1.ui.adapter.AccountAdapter

class MainActivity : AppCompatActivity(), AccountContract.View {

    private lateinit var presenter: AccountContract.Presenter
    private lateinit var binding: ActivityMainBinding
    private lateinit var accountAdapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AccountPresenter(view = this)

        initAdapter()
        initClicks()
        presenter.loadAccounts()


    }

    private fun initAdapter() {
        accountAdapter = AccountAdapter(
            onEdit = {
                showAccountDialog(it) { editAccount -> presenter.updateAccount(editAccount)  }
            },

            onStatusToogle = { id, isChecked ->
                presenter.patchAccountStatus(id, isChecked)
            },

            onDelete = {
                presenter.deleteAccount(it)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = accountAdapter
    }

    private fun initClicks(){
        with(binding){
            btnAdd.setOnClickListener {
                showAccountDialog { presenter.addAccount(it)  }
            }
        }
    }

    private fun showAccountDialog(accountModel: AccountModel? = null, action: (AccountModel) -> Unit){
        val dialogVB = ItemDialogBinding.inflate(LayoutInflater.from(this))
        with(dialogVB) {

            accountModel?.let {
                etName.setText(it.name)
                etBalance.setText(it.balance.toString())
                etCurrency.setText(it.currency)
            }

            val dialog = AlertDialog.Builder(this@MainActivity)
                .setView(dialogVB.root)
                .create()
            if (accountModel == null) {
                txtTitle.setText("Добавить счет")
                btnAdd.setText("Добавить")
            } else {
                txtTitle.setText("Изменить счет")
                btnAdd.setText("Изменить")
            }

            dialog.show()

            dialogVB.btnAdd.setOnClickListener {
                val name = dialogVB.etName.text.toString()
                val balance = dialogVB.etBalance.text.toString().toInt()
                val currency = dialogVB.etCurrency.text.toString()
                val newAccountModel = accountModel?.copy(
                    name = name,
                    balance = balance,
                    currency = currency,
                    isActive = true
                ) ?: AccountModel(
                    name = name,
                    balance = balance,
                    currency = currency,
                    isActive = true
                )


                action(newAccountModel!!)
                dialog.dismiss()
            }

            dialogVB.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    override fun showAccounts(accounts: List<AccountModel>) {
        accountAdapter.setItems(accounts)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}