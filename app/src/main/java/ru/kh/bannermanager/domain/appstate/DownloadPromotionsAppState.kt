package ru.kh.bannermanager.domain.appstate

import ru.kh.bannermanager.domain.entity.PromotionEntity

sealed class DownloadPromotionsAppState {
    object Loading: DownloadPromotionsAppState()
    data class Success(var data: ArrayList<PromotionEntity>): DownloadPromotionsAppState()
    data class Error(val error: Throwable): DownloadPromotionsAppState()
}