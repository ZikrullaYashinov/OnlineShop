package zikrulla.production.onlineshop.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.FragmentHomeBinding
import zikrulla.production.onlineshop.model.Resource
import zikrulla.production.onlineshop.util.Util.TAG
import zikrulla.production.onlineshop.view.CategoryAdapter
import zikrulla.production.onlineshop.view.OfferAdapter
import zikrulla.production.onlineshop.view.ProductAdapter

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var offerAdapter: OfferAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        offerAdapter = OfferAdapter(requireContext(), emptyList())
        productAdapter = ProductAdapter(requireContext(), emptyList()){
            val bundle = Bundle()
            bundle.putSerializable("product", it)
            findNavController().navigate(R.id.productDetailFragment, bundle)
        }
        categoryAdapter = CategoryAdapter(requireContext(), emptyList()) {
            viewModel.fetchProductsDB(it.id)
        }

        binding.apply {
            viewPager.adapter = offerAdapter
            productRv.adapter = productAdapter
            categoryRv.adapter = categoryAdapter
            categoryRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            TabLayoutMediator(tabLayout, viewPager) { tab, position -> }.attach()
            swipe.setOnRefreshListener {
                viewModel.fetchData()
            }
        }

        viewModel.getStateLoading().observe(viewLifecycleOwner) {
            swipeVisible(it)
        }
        viewModel.getOffers().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "Error: ${it.e.message}")
                    swipeVisible(false)
                }

                is Resource.Success -> {
                    offerAdapter.submitList(it.data)
                    Log.d(TAG, "onCreateView: ${it.data}")
                    swipeVisible(false)
                }
            }
        }
        viewModel.getCategories().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "Error: ${it.e.message}")
                    swipeVisible(false)
                }

                is Resource.Success -> {
                    categoryAdapter.submitList(it.data)
                    swipeVisible(false)
                }
            }
        }
        viewModel.getProducts().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "Error: ${it.e.message}")
                    swipeVisible(false)
                }

                is Resource.Success -> {
                    productAdapter.submitList(it.data)
                    swipeVisible(false)
                }
            }
        }

        return binding.root
    }

    private fun swipeVisible(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}