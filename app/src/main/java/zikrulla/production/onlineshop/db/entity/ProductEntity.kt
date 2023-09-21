package zikrulla.production.onlineshop.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val category_id: String,
    val created_at: String,
    val image: String,
    val name: String,
    val price: String,
    val updated_at: String,
    var cart_count: Int
):Serializable