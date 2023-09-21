package zikrulla.production.onlineshop.ui.changelanguage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yariksoffice.lingver.Lingver
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.FragmentChageLanguageBinding
import zikrulla.production.onlineshop.ui.MainActivity

class ChangeLanguageFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private lateinit var binding: FragmentChageLanguageBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChageLanguageBinding.inflate(inflater, container, false)

        binding.apply {
            val language = Lingver.getInstance().getLanguage()
            if (language == "uz") {
                uz.setTextColor(R.color.white)
                ViewCompat.setBackgroundTintList(
                    uz,
                    ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
                );
            } else if (language == "en") {
                en.setTextColor(R.color.white)
                ViewCompat.setBackgroundTintList(
                    en,
                    ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
                );
            }
            uz.setOnClickListener {
                Lingver.getInstance().setLocale(requireContext(), "uz")
                dismiss()
                requireActivity().finish()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            en.setOnClickListener {
                Lingver.getInstance().setLocale(requireContext(), "en")
                dismiss()
                requireActivity().finish()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChangeLanguageFragment()
    }
}