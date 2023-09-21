package zikrulla.production.onlineshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import zikrulla.production.onlineshop.databinding.ItemOfferBinding
import zikrulla.production.onlineshop.model.Offer
import zikrulla.production.onlineshop.util.Util.BASE_URL_IMAGES

class OfferAdapter(
    private val context: Context, private var list: List<Offer>
) : Adapter<OfferAdapter.Vh>() {

    inner class Vh(private val binding: ItemOfferBinding) : ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            Glide.with(context).load("${BASE_URL_IMAGES}${offer.image}").into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemOfferBinding.inflate(
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
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Offer>) {
        this.list = list
        notifyDataSetChanged()
    }

}