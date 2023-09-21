package zikrulla.production.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Category(
    val created_at: String,
    val icon: String,
    val id: Int,
    val parent_id: String,
    val title: String,
    val updated_at: String,
    var checked: Boolean = false
)