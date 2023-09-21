package zikrulla.production.onlineshop.ui.favourite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import zikrulla.production.onlineshop.api.repository.ShopRepository
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.model.request.ProductRequest
import zikrulla.production.onlineshop.util.PrefUtils
import zikrulla.production.onlineshop.util.Util.TAG
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    private val _stateProducts = MutableLiveData<Resource<List<ProductEntity>>>()
    private val _stateLoading = MutableLiveData<Boolean>()

    fun getProducts() = _stateProducts
    fun getLoading() = _stateLoading

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        val favouriteList = PrefUtils.getFavouriteList()
        Log.d(TAG, "fetchProducts: $favouriteList")
        if (favouriteList.isNotEmpty())
            viewModelScope.launch {
                repository.getProducts(ProductRequest(favouriteList)).onStart {
                    _stateLoading.postValue(true)
                    Log.d(TAG, "fetchProducts: true")
                }.catch {
                    _stateProducts.postValue(Resource.Error(it))
                    _stateLoading.postValue(false)
                }.collect {
                    when (it) {
                        is Resource.Error -> {
                            _stateProducts.postValue(Resource.Error(it.e))
                            _stateLoading.postValue(false)
                        }

                        is Resource.Success -> {
                            _stateProducts.postValue(Resource.Success(it.data))
                            _stateLoading.postValue(false)
                        }
                    }

                }
            }
        else
            _stateLoading.postValue(false)
    }

}