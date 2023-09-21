package zikrulla.production.onlineshop.util

import com.orhanobut.hawk.Hawk
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Cart
import zikrulla.production.onlineshop.model.Product

object PrefUtils {
    const val PREF_FAVOURITES = "pref_favourite"
    const val PREF_CART = "pref_cart"

    fun setFavourite(product: ProductEntity) {
        val items = Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
        if (items.filter { it == product.id }.firstOrNull() != null) {
            items.remove(product.id)
        } else {
            items.add(product.id)
        }
        Hawk.put(PREF_FAVOURITES, items)
    }

    fun setCart(product: ProductEntity) {
        val items = Hawk.get(PREF_CART, arrayListOf<Cart>())
        var position = 0
        var cart: Cart? = null
        for (i in items.indices) {
            if (items[i].productId == product.id) {
                position = i
                cart = items[i]
            }
        }
        if (cart != null) {
            if (product.cart_count > 0) {
                cart.count = product.cart_count
                items[position] = cart
            } else {
                cart.count = 1
                items.remove(cart)
            }
        } else {
            items.add(Cart(product.id, product.cart_count))
        }
        Hawk.put(PREF_CART, items)
    }

    fun getCartCount(product: ProductEntity): Int {
        val items = Hawk.get(PREF_CART, arrayListOf<Cart>())
        return items.filter { it.productId == product.id }.firstOrNull()?.count ?: 0
    }

    fun getFavouriteList(): ArrayList<Int> {
        return Hawk.get(PREF_FAVOURITES, arrayListOf())
    }

    fun getCartList(): ArrayList<Cart> {
        return Hawk.get(PREF_CART, arrayListOf())
    }

    fun checkFavourite(product: ProductEntity): Boolean {
        val items = Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
        return items.filter { it == product.id }.firstOrNull() != null
    }

    fun checkCart(product: ProductEntity): Boolean {
        val items = Hawk.get(PREF_CART, arrayListOf<Int>())
        return items.filter { it == product.id }.firstOrNull() != null
    }
}