package com.ara.month4_lesson1.data.model

data class AccountModel (
    val id: String? = null,
    val name: String,
    val balance: Int,
    val currency: String,
    val isActive: Boolean
)