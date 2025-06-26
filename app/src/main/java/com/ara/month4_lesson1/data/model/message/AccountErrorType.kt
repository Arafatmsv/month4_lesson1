package com.ara.month4_lesson1.data.model.message

enum class AccountErrorType(val message: String) {
    ACCOUNT_FETCH_ERROR("Ошибка загрузки счетов"),
    ACCOUNT_ADD_ERROR("Ошибка добавление счета"),
    NETWORK_ERROR("Ошибка сети")

}
