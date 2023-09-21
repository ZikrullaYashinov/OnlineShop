package zikrulla.production.onlineshop.ui.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.FragmentFavouriteBinding
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.util.Util.TAG
import zikrulla.production.onlineshop.view.ProductAdapter

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private var isInit = true

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        if (!isInit)
            viewModel.fetchProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        isInit = false
        adapter = ProductAdapter(requireContext(), emptyList()) {
            val bundle = Bundle()
            bundle.putSerializable("product", it)
            findNavController().navigate(R.id.productDetailFragment, bundle)
        }

        binding.apply {
            productRv.adapter = adapter
            swipe.setOnRefreshListener {
                viewModel.fetchProducts()
            }
        }

        viewModel.getProducts().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "onCreateView: ${it.e}")
                }

                is Resource.Success -> {
                    Log.d(TAG, "onCreateView: ${it.data}")
                    adapter.submitList(it.data)
                }
            }
        }

        viewModel.getLoading().observe(viewLifecycleOwner) {
            swipeVisible(it)
        }

        return binding.root
    }

    private fun swipeVisible(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }
}