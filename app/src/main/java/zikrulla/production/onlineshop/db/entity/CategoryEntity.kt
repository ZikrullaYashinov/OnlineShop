package zikrulla.production.onlineshop.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    val created_at: String,
    val icon: String,
    @PrimaryKey
    val id: Int,
    val parent_id: String,
    val title: String,
    val updated_at: String,
    var checked: Boolean = false
)