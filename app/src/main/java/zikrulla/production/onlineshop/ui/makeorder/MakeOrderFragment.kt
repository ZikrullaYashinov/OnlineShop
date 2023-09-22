package zikrulla.production.onlineshop.ui.makeorder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import zikrulla.production.onlineshop.MapsActivity
import zikrulla.production.onlineshop.databinding.FragmentMakeOrderBinding
import zikrulla.production.onlineshop.db.entity.ProductEntity
import zikrulla.production.onlineshop.model.Address
import zikrulla.production.onlineshop.model.Product
import zikrulla.production.onlineshop.model.ProductList
import zikrulla.production.onlineshop.util.Util.TAG

class MakeOrderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            products = (requireArguments().getSerializable("products") as ProductList).list
        }
    }

    private lateinit var binding: FragmentMakeOrderBinding
    private var address: Address? = null
    private var products: List<ProductEntity>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeOrderBinding.inflate(inflater, container, false)

        binding.apply {
            address.setOnClickListener {
                val intent = Intent(requireContext(), MapsActivity::class.java)
                startActivity(intent)
            }
            back.setOnClickListener { findNavController().popBackStack() }
        }
        if (products != null)
            calculate(products!!)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        return binding.root
    }

    private fun calculate(products: List<ProductEntity>) {
        Thread {
            var sum: Double = 0.0
            products.forEach {
                val price = it.price.replace(" ", "").toDouble()
                sum += (it.cart_count ?: 0) * price
            }
            Log.d(TAG, "calculate: $sum")
            binding.totalAmount.text = format(sum.toLong())
        }.start()
    }

    private fun format(sum: Long): String {
        var result = ""
        var space = 0
        val list = sum.toString().split("").reversed()
        for (i in list) {
            if (i == "") continue
            space++
            result += i
            if (space == 3){
                result += " "
                space = 0
            }
        }
        return result.reversed()
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    fun onEvent(address: Address) {
        this.address = address
        binding.address.setText("${address.latitude} ${address.longitude}")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }
}