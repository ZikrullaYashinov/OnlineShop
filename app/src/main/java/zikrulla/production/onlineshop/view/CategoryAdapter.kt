package zikrulla.production.onlineshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.ItemCategoryBinding
import zikrulla.production.onlineshop.db.entity.CategoryEntity
import zikrulla.production.onlineshop.model.Category

class CategoryAdapter(
    private val context: Context,
    private var list: List<CategoryEntity>,
    private val itemClick: (CategoryEntity) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.Vh>() {

    inner class Vh(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
        fun bind(category: CategoryEntity) {
            binding.apply {
                if (category.checked) {
                    text.setTextColor(Color.WHITE)
                    card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPrimary
                        )
                    )
                } else {
                    text.setTextColor(Color.BLACK)
                    card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                }
                text.text = category.title
                binding.card.setOnClickListener {
                    itemClick.invoke(category)
                    list.forEach {
                        it.checked = false
                    }
                    category.checked = true
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CategoryEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

}