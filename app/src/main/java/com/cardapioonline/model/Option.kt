package com.cardapioonline.model

data class Option(
    val image: Int,
    val name: String,
    val price: Double,
    val time: Int,
    var isChecked: Boolean = false
)
