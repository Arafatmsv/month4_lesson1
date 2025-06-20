package com.ara.month4_lesson1.data.model.message

enum class AccountSuccessType(val message: String) {
    ACCOUNT_STATUS_SUCCESS("Состояние статуса счета обновлен"),
    ACCOUNT_CHANGE_SUCCESS("Счет успешно обновлен"),
    ACCOUNT_DELETE_SUCCESS("Счет успешно удален"),
}