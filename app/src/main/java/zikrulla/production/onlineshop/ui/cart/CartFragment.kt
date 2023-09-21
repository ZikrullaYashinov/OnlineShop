package zikrulla.production.onlineshop.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.FragmentCartBinding
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.model.ProductList
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.util.Util.TAG
import zikrulla.production.onlineshop.view.CartAdapter

@AndroidEntryPoint
class CartFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel: CartViewModel by viewModels()
    private var productList: List<ProductEntity>? = null

    override fun onStart() {
        super.onStart()
        viewModel.fetchCarts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        adapter = CartAdapter(requireContext(), emptyList()) {

        }

        binding.apply {
            cartRv.adapter = adapter
            swipe.setOnRefreshListener {
                viewModel.fetchCarts()
            }
            makeOrder.setOnClickListener {
                if (productList != null) {
                    val bundle = Bundle()
                    bundle.putSerializable("products", ProductList(productList!!))
                    findNavController().navigate(R.id.makeOrderFragment, bundle)
                }
            }
        }

        viewModel.getProducts().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "onCreateView: ${it.e}")
                }

                is Resource.Success -> {
                    adapter.submitList(it.data)
                    productList = it.data
                    binding.makeOrder.isEnabled = true
                }
            }
        }

        viewModel.getLoading().observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = it
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}