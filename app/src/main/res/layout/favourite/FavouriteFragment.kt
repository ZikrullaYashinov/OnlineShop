package zikrulla.production.onlinesavdo.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zikrulla.production.onlinesavdo.R
import zikrulla.production.onlinesavdo.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }
}