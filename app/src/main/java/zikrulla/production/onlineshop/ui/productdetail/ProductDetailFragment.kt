package zikrulla.production.onlineshop.ui.productdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.FragmentProductDetailBinding
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.util.PrefUtils
import zikrulla.production.onlineshop.util.Util

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = requireArguments().getSerializable("product") as ProductEntity?
        }
    }

    private lateinit var binding: FragmentProductDetailBinding
    private var product: ProductEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        binding.apply {
            productName.text = product?.name
            tvTitle.text = product?.name
            productPrice.text = product?.price
            Glide.with(requireContext()).load("${Util.BASE_URL_IMAGES}${product?.image}")
                .into(productImage)
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            favouriteCard.setOnClickListener {
                PrefUtils.setFavourite(product!!)
                if (PrefUtils.checkFavourite(product!!)) {
                    favouriteImage.setImageResource(R.drawable.ic_favorite)
                } else {
                    favouriteImage.setImageResource(R.drawable.ic_favourite)
                }
            }
            if (PrefUtils.checkFavourite(product!!)) {
                favouriteImage.setImageResource(R.drawable.ic_favorite)
            } else {
                favouriteImage.setImageResource(R.drawable.ic_favourite)
            }
            addCart.isVisible = PrefUtils.getCartCount(product!!) <= 0
            addCart.setOnClickListener {
                product!!.cart_count = 1
                PrefUtils.setCart(product!!)
                addCart.isVisible = PrefUtils.getCartCount(product!!) <= 0
                Toast.makeText(requireContext(),
                    getString(R.string.product_aded_to_cart), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        return binding.root
    }
}