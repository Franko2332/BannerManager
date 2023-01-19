package ru.kh.bannermanager.domain.appstate

sealed class AddPromotionAppState{
    object Loading: AddPromotionAppState()
    object Success: AddPromotionAppState()
    data class Error(val error: Throwable): AddPromotionAppState()
}
