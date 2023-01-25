package ru.kh.bannermanager.domain.entity

import com.google.firebase.Timestamp

class PromotionEntity() {
    var title: String? = ""
    var description: String? = ""
    var buttonLink: String? = ""
    var buttonText: String? = ""
    var isSite: String? = ""
    var lang: String? = ""
    var link: String? = ""
    var start: Timestamp? = Timestamp.now()
    var end: Timestamp? = Timestamp.now()
    var id: String? = ""

    constructor(
        title: String?,
        description: String?,
        buttonLink: String?,
        buttonText: String?,
        isSite: String?,
        lang: String?,
        link: String?,
        start: Timestamp?,
        end: Timestamp?,
        id: String?
    ): this() {
        this.title = title
        this.description = description
        this.buttonLink = buttonLink
        this.buttonText = buttonText
        this.isSite = isSite
        this.link = link
        this.lang = lang
        this.start = start
        this.end = end
        this.id = id
    }
}