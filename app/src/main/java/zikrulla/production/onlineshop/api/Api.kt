package zikrulla.production.onlineshop.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.BaseResponse
import zikrulla.production.onlineshop.model.Category
import zikrulla.production.onlineshop.model.Offer
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.model.request.ProductRequest

interface Api {

    @GET("get_offers")
    suspend fun getOffers(): Response<BaseResponse<List<Offer>>>

    @GET("get_categories")
    suspend fun getCategories(): Response<BaseResponse<List<Category>>>

    @GET("get_top_products")
    suspend fun getProducts(): Response<BaseResponse<List<Product>>>

    @GET("get_products/{category_id}")
    suspend fun getProducts(@Path("category_id") categoryId: Int): Response<BaseResponse<List<Product>>>

    @POST("get_products_by_ids")
    suspend fun postProducts(@Body request: ProductRequest): Response<BaseResponse<List<ProductEntity>>>
}