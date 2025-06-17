package com.ara.month4_lesson1.data.api


import com.ara.month4_lesson1.data.model.AccountModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AccountApi {

    @GET("accounts")
    fun getAccounts(): Call<List<AccountModel>>

    @POST("accounts")
    fun createAccount(@Body accountModel: AccountModel): Call<AccountModel>

}