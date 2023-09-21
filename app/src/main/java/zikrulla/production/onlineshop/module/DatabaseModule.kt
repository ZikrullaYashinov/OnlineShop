package zikrulla.production.onlineshop.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zikrulla.production.onlineshop.api.Api
import zikrulla.production.onlineshop.db.AppDatabase
import zikrulla.production.onlineshop.db.CategoriesDao
import zikrulla.production.onlineshop.db.ProductsDao
import zikrulla.production.onlineshop.util.Util
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductsDao {
        return appDatabase.productsDao()
    }

    @Singleton
    @Provides
    fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao {
        return appDatabase.categoriesDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
            .fallbackToDestructiveMigration().build()
    }

}