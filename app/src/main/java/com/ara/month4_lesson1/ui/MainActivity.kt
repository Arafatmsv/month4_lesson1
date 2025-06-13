package com.ara.month4_lesson1.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.ara.month4_lesson1.R
import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.databinding.ActivityMainBinding
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
        presenter.loadAccounts()


    }

    private fun initAdapter() {
        accountAdapter = AccountAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = accountAdapter
    }

    override fun showAccounts(accounts: List<AccountModel>) {
        accountAdapter.setItems(accounts)
    }

}