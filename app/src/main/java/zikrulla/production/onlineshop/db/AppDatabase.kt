package zikrulla.production.onlineshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import zikrulla.production.onlineshop.db.entity.CategoryEntity
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Category
import zikrulla.production.onlineshop.model.Product

@Database(
    entities = [ProductEntity::class, CategoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao
    abstract fun categoriesDao(): CategoriesDao

}