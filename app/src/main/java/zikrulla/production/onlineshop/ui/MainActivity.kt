package zikrulla.production.onlineshop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.ActivityMainBinding
import zikrulla.production.onlineshop.ui.changelanguage.ChangeLanguageFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menu.setOnClickListener {
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        val navController = findNavController(R.id.fragment_container_view)
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}