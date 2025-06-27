package com.ara.month4_lesson1.ui


import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.databinding.ActivityMainBinding
import com.ara.month4_lesson1.databinding.ItemDialogBinding
import com.ara.month4_lesson1.viewModel.AccountViewModel
import com.ara.month4_lesson1.ui.adapter.AccountAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var accountAdapter: AccountAdapter
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initAdapter()
        initClicks()
        viewModel.loadAccounts()
        subscribeToLiveData()


    }

    private fun subscribeToLiveData() {
        viewModel.accounts.observe(this) {
            accountAdapter.setItems(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.successMessage.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
        accountAdapter = AccountAdapter(
            onEdit = {
                showAccountDialog(it) { editAccount -> viewModel.updateAccount(editAccount)  }
            },

            onStatusToogle = { id, isChecked ->
                viewModel.patchAccountStatus(id, isChecked)
            },

            onDelete = {
                viewModel.deleteAccount(it)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = accountAdapter
    }

    private fun initClicks(){
        with(binding){
            btnAdd.setOnClickListener {
                showAccountDialog { viewModel.addAccount(it)  }
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
                val newAccountModel = AccountModel(
                    accountId = accountModel?.accountId,
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


}