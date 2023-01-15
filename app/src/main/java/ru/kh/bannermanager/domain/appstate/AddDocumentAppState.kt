package ru.kh.bannermanager.domain.appstate

import ru.kh.bannermanager.domain.entity.PromotionEntity
import java.lang.Exception

sealed class AddDocumentAppState{
    object Loading: DownloadDocumentsAppState()
    object Success: DownloadDocumentsAppState()
    data class Error(val error: Throwable): DownloadDocumentsAppState()
}
