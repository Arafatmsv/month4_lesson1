package com.ara.month4_lesson1.presenter

import android.accounts.Account
import com.ara.month4_lesson1.data.model.AccountModel

interface AccountContract {
    interface View {
        fun showAccounts(accounts: List<AccountModel >)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadAccounts()
        fun addAccount(accountModel: AccountModel)
    }

}