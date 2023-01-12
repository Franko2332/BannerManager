package ru.kh.bannermanager.domain.appstate

import ru.kh.bannermanager.domain.entity.PromotionEntity
import java.lang.Exception

sealed class AppState {
    object Loading: AppState()
    data class Success(var data: ArrayList<PromotionEntity>): AppState()
    data class Error(val error: Exception): AppState()
}