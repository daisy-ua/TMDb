package com.daisy.domain.models

import java.util.*

enum class ProductionStatus(val status: String) {

    RUMORED("Rumored"),

    PLANNED("Planned"),

    IN_PROD("In Production"),

    POST_PROD("Post Production"),

    RELEASED("Released"),

    CANCELED("Canceled");

    companion object {
        fun getType(name: String) = valueOf(name.uppercase(Locale.getDefault()))
    }
}
