package com.ara.month4_lesson1.data.api


import com.ara.month4_lesson1.data.model.AccountModel
import com.ara.month4_lesson1.data.model.AccountStatusPatch
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface AccountApi {

    @GET("accounts")
    fun getAccounts(): Call<List<AccountModel>>

    @POST("accounts")
    fun createAccount(@Body accountModel: AccountModel): Call<AccountModel>

    @PUT("accounts/{accountId}")
    fun updateAccountFully(
        @Path("accountId") id: String,
        @Body accountModel: AccountModel
    ): Call<Unit>

    @DELETE("accounts/{accountId}")
    fun accountDelete(
        @Path("accountId") id: String
    ): Call<Unit>

    @PATCH("accounts/{accountId}")
    fun patchAccountStatus(
        @Path("accountId") id: String,
        @Body accountStatusPatch: AccountStatusPatch
    ): Call<Unit>

}