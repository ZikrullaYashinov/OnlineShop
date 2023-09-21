package zikrulla.production.onlineshop.model.request

import com.google.gson.annotations.SerializedName

data class ProductRequest(
    @SerializedName("products")
    val products: List<Int>
)