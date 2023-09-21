package zikrulla.production.onlineshop.model

import zikrulla.production.onlineshop.db.entity.ProductEntity
import java.io.Serializable

data class ProductList(
    val list: List<ProductEntity>
):Serializable
