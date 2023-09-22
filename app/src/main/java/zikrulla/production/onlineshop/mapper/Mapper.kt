package zikrulla.production.onlineshop.mapper

import zikrulla.production.onlineshop.db.entity.CategoryEntity
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Category
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

fun Category.mapToCategoryEntity(category: Category): CategoryEntity {
    return CategoryEntity(
        category.created_at,
        category.icon,
        category.id,
        category.parent_id,
        category.title,
        category.updated_at,
        category.checked
    )
}