package com.ara.month4_lesson1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ara.month4_lesson1.data.api.ApiClient
import com.ara.month4_lesson1.data.model.message.AccountErrorType
import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.data.model.AccountStatusPatch
import com.ara.month4_lesson1.data.model.message.AccountSuccessType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel:ViewModel() {

    private val _accounts = MutableLiveData<List<AccountModel>>()
    val accounts: LiveData<List<AccountModel>> = _accounts

    private val _errorMessage = MutableLiveData<AccountErrorType>()
    val errorMessage: LiveData<AccountErrorType> = _errorMessage

    private val _successMessage = MutableLiveData<AccountSuccessType>()
    val successMessage: LiveData<AccountSuccessType> = _successMessage

    fun loadAccounts() {
        ApiClient.accountApi.getAccounts().handleResponse(
            onSuccess = { _accounts.value = it },
            onError = { _errorMessage.value = AccountErrorType.ACCOUNT_FETCH_ERROR }
        )
    }

    fun addAccount(accountModel: AccountModel) {
        ApiClient.accountApi.createAccount(accountModel).handleResponse(
            onSuccess = {loadAccounts()},
            onError = {_errorMessage.value = AccountErrorType.ACCOUNT_ADD_ERROR}
        )
    }

    fun updateAccount(accountModel: AccountModel) {
        ApiClient.accountApi.updateAccountFully(
            id = accountModel.accountId!!,
            accountModel = accountModel
        ).handleResponse(
            onSuccess = {
                _successMessage.value = AccountSuccessType.ACCOUNT_CHANGE_SUCCESS
                loadAccounts() }
        )
    }

    fun patchAccountStatus(id: String, isActive: Boolean) {
        ApiClient.accountApi.patchAccountStatus(id, AccountStatusPatch(isActive)).handleResponse(
            onSuccess = {
                _successMessage.value = AccountSuccessType.ACCOUNT_STATUS_SUCCESS
                loadAccounts() }
        )
    }

    fun deleteAccount(id: String) {
        ApiClient.accountApi.accountDelete(id).handleResponse(
            onSuccess = {
                _successMessage.value = AccountSuccessType.ACCOUNT_DELETE_SUCCESS
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