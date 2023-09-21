package zikrulla.production.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zikrulla.production.onlineshop.db.entity.CategoryEntity
import zikrulla.production.onlineshop.model.Category
import zikrulla.production.onlineshop.model.Product

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

}