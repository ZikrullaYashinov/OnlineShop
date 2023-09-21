package zikrulla.production.onlineshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zikrulla.production.onlineshop.databinding.ItemCartBinding
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.util.Util

class CartAdapter (
    private val context: Context,
    private var list: List<ProductEntity>,
    private val itemClick: (ProductEntity) -> Unit
) : RecyclerView.Adapter<CartAdapter.Vh>() {

    inner class Vh(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductEntity) {
            binding.apply {
                Glide.with(context).load("${Util.BASE_URL_IMAGES}${product.image}").into(image)
                price.text = product.price
                productName.text = product.name
                count.text = product.cart_count.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val product = list[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            itemClick.invoke(product)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ProductEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

}
