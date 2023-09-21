package zikrulla.production.onlineshop.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import zikrulla.production.onlineshop.R
import zikrulla.production.onlineshop.databinding.ActivitySplashBinding
import zikrulla.production.onlineshop.databinding.FragmentChageLanguageBinding
import zikrulla.production.onlineshop.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animationView.postDelayed({
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}