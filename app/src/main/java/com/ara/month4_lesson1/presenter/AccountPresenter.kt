package com.ara.month4_lesson1.presenter

import com.ara.month4_lesson1.data.model.AccountModel

class AccountPresenter(val view: AccountContract.View): AccountContract.Presenter {
    override fun loadAccounts() {
        val testAccountList = listOf(
            AccountModel(
                id = "1",
                name = "M-bank",
                balance = 1527,
                currency = "KGS",
                isActive = true
            ),

            AccountModel(
                id = "2",
                name = "Optima bank",
                balance = 34566,
                currency = "KGS",
                isActive = true
            ),

            AccountModel(
                id = "3",
                name = "Ayil bank",
                balance = 500,
                currency = "USD",
                isActive = true
            ),

            AccountModel(
                id = "4",
                name = "Bakai bank",
                balance = 100000,
                currency = "KGS",
                isActive = true
            ),

            AccountModel(
                id = "5",
                name = "T-bank",
                balance = 5000,
                currency = "RUB",
                isActive = true
            ),

            AccountModel(
                id = "6",
                name = "0!bank",
                balance = 12345,
                currency = "KGS",
                isActive = true
            )
        )
        view.showAccounts(testAccountList)
    }
}