package zikrulla.production.onlinesavdo.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import zikrulla.production.onlinesavdo.R
import zikrulla.production.onlinesavdo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private lateinit var binding: FragmentHomeBinding
    private val images = arrayOf(R.drawable.ic_home, R.drawable.ic_cart)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            carouselView.size = images.size
            carouselView.setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.image_view)
                imageView.setImageDrawable(resources.getDrawable(images[position]))
            }
            carouselView.show()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}