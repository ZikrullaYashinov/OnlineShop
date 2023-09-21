package zikrulla.production.onlineshop.model

import androidx.room.Entity
import java.io.Serializable

data class Product(
    val category_id: String,
    val created_at: String,
    val id: Int,
    val image: String,
    val name: String,
    val price: String,
    val updated_at: String,
    var cart_count: Int
) : Serializable