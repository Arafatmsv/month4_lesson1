package com.ara.month4_lesson1.presenter

import com.ara.month4_lesson1.data.api.ApiClient
import com.ara.month4_lesson1.data.model.message.AccountErrorType
import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.data.model.AccountStatusPatch
import com.ara.month4_lesson1.data.model.message.AccountSuccessType
import com.ara.month4_lesson1.data.model.message.errorMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountPresenter(val view: AccountContract.View): AccountContract.Presenter {
    override fun loadAccounts() {
        ApiClient.accountApi.getAccounts().handleResponse(
            onSuccess = {view.showAccounts(it)},
            onError = {view.showError(AccountErrorType.ACCOUNT_FETCH_ERROR.errorMessage(it))}
        )
    }

    override fun addAccount(accountModel: AccountModel) {
        ApiClient.accountApi.createAccount(accountModel).handleResponse(
            onSuccess = {loadAccounts()},
            onError = {view.showError(AccountErrorType.ACCOUNT_ADD_ERROR.errorMessage(it))}
        )
    }

    override fun updateAccount(accountModel: AccountModel) {
        ApiClient.accountApi.updateAccountFully(
            id = accountModel.accountId!!,
            accountModel = accountModel
        ).handleResponse(
            onSuccess = {
                view.showSuccess(AccountSuccessType.ACCOUNT_CHANGE_SUCCESS.message)
                loadAccounts() },
        )
    }

    override fun patchAccountStatus(id: String, isActive: Boolean) {
        ApiClient.accountApi.patchAccountStatus(id, AccountStatusPatch(isActive)).handleResponse(
            onSuccess = {
                view.showSuccess(AccountSuccessType.ACCOUNT_STATUS_SUCCESS.message)
                loadAccounts() }
        )
    }

    override fun deleteAccount(id: String) {
        ApiClient.accountApi.accountDelete(id).handleResponse(
            onSuccess = {
                view.showSuccess(AccountSuccessType.ACCOUNT_DELETE_SUCCESS.message)
                loadAccounts() }
        )
    }

    fun <T> Call <T>.handleResponse(
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = {}
    ) {
        enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError(response.code().toString())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError("${AccountErrorType.NETWORK_ERROR}: ${t.message}")
            }


        })
    }

}