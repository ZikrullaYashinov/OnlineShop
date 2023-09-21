package zikrulla.production.onlineshop.mapper

import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Product

fun Product.mapToProductEntity(product: Product): ProductEntity {
    return ProductEntity(
        product.id,
        product.category_id,
        product.created_at,
        product.image,
        product.name,
        product.price,
        product.updated_at,
        product.cart_count
    )
}