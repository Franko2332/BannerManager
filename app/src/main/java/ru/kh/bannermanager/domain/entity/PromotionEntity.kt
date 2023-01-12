package ru.kh.bannermanager.domain.entity

import com.google.firebase.Timestamp

class PromotionEntity() {
    var title: String? = ""
    var description: String? = ""
    var link: String? = ""
    var isSite: String? = ""
    var lang: String? = ""
    var start: Timestamp? = Timestamp.now()
    var end: Timestamp? = Timestamp.now()
    var id: String? = ""

    constructor(
        title: String?,
        description: String?,
        link: String?,
        isSite: String?,
        lang: String?,
        start: Timestamp?,
        end: Timestamp?,
        id: String?
    ): this() {
        this.title = title
        this.description = description
        this.link = link
        this.isSite = isSite
        this.lang = lang
        this.start = start
        this.end = end
        this.id = id
    }
}