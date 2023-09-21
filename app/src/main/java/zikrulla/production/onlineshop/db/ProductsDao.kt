package zikrulla.production.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Product

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getTopProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    fun getProducts(categoryId: Int): Flow<List<ProductEntity>>

}