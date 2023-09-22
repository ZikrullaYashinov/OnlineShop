package zikrulla.production.onlineshop.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import zikrulla.production.onlineshop.api.repository.ShopRepository
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.model.request.ProductRequest
import zikrulla.production.onlineshop.util.PrefUtils
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    private val _stateProducts = MutableLiveData<Resource<List<ProductEntity>>>()

    private val _stateLoading = MutableLiveData<Boolean>()
    fun getProducts() = _stateProducts
    fun getLoading() = _stateLoading

    init {
        fetchCarts()
    }

    fun fetchCarts() {
        val cartList = PrefUtils.getCartList()
        if (cartList.isNotEmpty()) {
            viewModelScope.launch {
                repository.getProducts(ProductRequest(cartList.map { it.productId })).onStart {
                    _stateLoading.postValue(true)
                }.catch {
                    _stateProducts.postValue(Resource.Error(it))
                    _stateLoading.postValue(false)
                }.collect {
                    when (it) {
                        is Resource.Error -> {
                            _stateProducts.postValue(Resource.Error(it.e))
                        }

                        is Resource.Success -> {
                            val productList = it.data
                            for (i in cartList.indices) {
                                productList[i].cart_count = cartList[i].count
                            }
                            _stateProducts.postValue(Resource.Success(productList))
                        }
                    }
                    _stateLoading.postValue(false)
                }
            }
        } else {
            _stateLoading.value = false
        }
    }

    fun setCart(
        list: List<ProductEntity>?,
        productEntity: ProductEntity,
        position: Int,
        isIncrement: Boolean
    ) {
        if (isIncrement) productEntity.cart_count++ else productEntity.cart_count--
        PrefUtils.setCart(productEntity)

        if (productEntity.cart_count <= 0)
            (list as ArrayList).remove(productEntity)
        else
            (list as ArrayList)[position] = productEntity

        _stateProducts.postValue(Resource.Success(list))
    }
}