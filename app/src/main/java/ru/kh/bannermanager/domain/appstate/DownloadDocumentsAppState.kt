package ru.kh.bannermanager.domain.appstate

import ru.kh.bannermanager.domain.entity.PromotionEntity
import java.lang.Exception

sealed class DownloadDocumentsAppState {
    object Loading: DownloadDocumentsAppState()
    data class Success(var data: ArrayList<PromotionEntity>): DownloadDocumentsAppState()
    data class Error(val error: Throwable): DownloadDocumentsAppState()
}