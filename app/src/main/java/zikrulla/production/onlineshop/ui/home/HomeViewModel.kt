package zikrulla.production.onlineshop.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import zikrulla.production.onlineshop.api.repository.ShopRepository
import zikrulla.production.onlineshop.db.entity.CategoryEntity
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Category
import zikrulla.production.onlineshop.model.Offer
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.util.Util.TAG
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    private var _stateOffers = MutableLiveData<Resource<List<Offer>>>()
    private var _stateCategories = MutableLiveData<Resource<List<CategoryEntity>>>()
    private var _stateProducts = MutableLiveData<Resource<List<ProductEntity>>>()
    private var _stateLoading = MutableLiveData<Boolean>()

    private var loadingMap =
        mutableMapOf("getOffers" to true, "getCategories" to true, "getProducts" to true)

    private fun getLoading(): Boolean {
        var result = false
        loadingMap.forEach { (_, u) ->
            result = u || result
        }
        return result
    }

    private fun setLoading(key: String, value: Boolean) {
        loadingMap[key] = value
        _stateLoading.postValue(getLoading())
    }

    fun getOffers() = _stateOffers
    fun getCategories() = _stateCategories
    fun getProducts() = _stateProducts
    fun getStateLoading() = _stateLoading

    init {
        fetchData()
    }

    fun fetchData() {
        fetchOffers()
        fetchCategories()
        fetchProducts()

        fetchProductsDB()
        fetchCategoriesDB()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            repository.getCategories().onStart {
                setLoading("getCategories", true)
            }.catch {
                _stateCategories.postValue(Resource.Error(it))
                setLoading("getCategories", false)
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        _stateCategories.postValue(Resource.Error(it.e))
                    }

                    is Resource.Success -> {
                        repository.insertCategoriesDB(it.data)
                    }
                }
                setLoading("getCategories", false)
            }
        }
    }

    private fun fetchCategoriesDB() {
        viewModelScope.launch {
            repository.getCategoriesDB().onStart {
                setLoading("getCategories", true)
            }.catch {
                _stateCategories.postValue(Resource.Error(it))
                setLoading("getCategories", false)
            }.collect {
                _stateCategories.postValue(Resource.Success(it))
                setLoading("getCategories", false)
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getProducts().onStart {
                setLoading("getProducts", true)
            }.catch {
                _stateProducts.postValue(Resource.Error(it))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        _stateProducts.postValue(Resource.Error(it.e))
                        setLoading("getProducts", false)
                    }

                    is Resource.Success -> {
                        repository.insertProductsDB(it.data)
                        setLoading("getProducts", false)
                    }
                }
            }
        }
    }

    private fun fetchProductsDB() {
        viewModelScope.launch {
            repository.getProductsDB().onStart {
                setLoading("getProducts", true)
            }.catch {
                _stateProducts.postValue(Resource.Error(it))
                setLoading("getProducts", false)
            }.collect {
                _stateProducts.postValue(Resource.Success(it))
                setLoading("getProducts", false)
            }
        }
    }

    fun fetchProducts(categoryId: Int) {
        viewModelScope.launch {
            repository.getProducts(categoryId).onStart {
                setLoading("getProducts", true)
            }.catch {
                _stateProducts.postValue(Resource.Error(it))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        _stateProducts.postValue(Resource.Error(it.e))
                        setLoading("getProducts", false)
                    }

                    is Resource.Success -> {
                        setLoading("getProducts", false)
                        Log.d(TAG, "fetchProducts: $categoryId ${it.data}")
                    }
                }
            }
        }
    }

    fun fetchProductsDB(categoryId: Int) {
        viewModelScope.launch {
            repository.getProductsDB(categoryId).onStart {
                setLoading("getProducts", true)
            }.catch {
                _stateProducts.postValue(Resource.Error(it))
                setLoading("getProducts", false)
            }.collect {
                _stateProducts.postValue(Resource.Success(it))
                setLoading("getProducts", false)
            }
        }
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            repository.getOffers().onStart {
                setLoading("getOffers", true)
            }.catch {
                _stateOffers.postValue(Resource.Error(it))
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        _stateOffers.postValue(Resource.Error(it.e))
                        setLoading("getOffers", false)
                    }

                    is Resource.Success -> {
                        _stateOffers.postValue(Resource.Success(it.data))
                        setLoading("getOffers", false)
                    }
                }
            }
        }
    }

}