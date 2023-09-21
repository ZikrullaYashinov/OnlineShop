package zikrulla.production.onlineshop.model

data class BaseResponse<T>(
    val `data`: T,
    val error_code: Int,
    val message: String,
    val success: Boolean
)