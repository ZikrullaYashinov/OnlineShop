package zikrulla.production.onlineshop.api.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import zikrulla.production.onlineshop.api.Api
import zikrulla.production.onlineshop.db.ProductsDao
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.mapper.mapToProductEntity
import zikrulla.production.onlineshop.model.Category
import zikrulla.production.onlineshop.model.Offer
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.model.request.ProductRequest
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val api: Api,
    private val productsDao: ProductsDao
) {

    fun getOffers(): Flow<Resource<List<Offer>>> {
        return flow {
            val response = api.getOffers()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()?.data!!))
            } else {
                emit(Resource.Error(Throwable(response.message())))
            }
        }
    }

    fun getProducts(): Flow<Resource<List<Product>>> {
        return flow {
            val response = api.getProducts()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()?.data!!))
            } else {
                emit(Resource.Error(Throwable(response.message())))
            }
        }
    }

    fun getProducts(request: ProductRequest): Flow<Resource<List<ProductEntity>>> {
        return flow {
            val response = api.postProducts(request)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()?.data!!))
            } else {
                emit(Resource.Error(Throwable(response.message())))
            }
        }
    }

    fun getProducts(categoryId: Int): Flow<Resource<List<Product>>> {
        return flow {
            val response = api.getProducts(categoryId)
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()?.data!!))
            } else {
                emit(Resource.Error(Throwable(response.message())))
            }
        }
    }
    fun getProductsDB(categoryId: Int): Flow<List<ProductEntity>> {
        return productsDao.getProducts(categoryId)
    }

    fun getCategories(): Flow<Resource<List<Category>>> {
        return flow {
            val response = api.getCategories()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Resource.Success(response.body()?.data!!))
            } else {
                emit(Resource.Error(Throwable(response.message())))
            }
        }
    }

    fun getProductsDB(): Flow<List<ProductEntity>> {
        return productsDao.getTopProducts()
    }

    suspend fun insertProductsDB(list: List<Product>) {
        productsDao.insertAll(list.map { it.mapToProductEntity(it) })
    }

}
