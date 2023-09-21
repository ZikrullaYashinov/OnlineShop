package zikrulla.production.onlineshop.model

import java.io.Serializable

data class Address(
    val address: String,
    val latitude: Double,
    val longitude: Double
) : Serializable
