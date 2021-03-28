package com.ngochuy.ecommerce.feature.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.startActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.doOnLayout
import com.ngochuy.ecommerce.R
import kotlinx.android.synthetic.main.activity_splash.*
import com.ngochuy.ecommerce.feature.main.MainActivity


class SplashActivity : AppCompatActivity() , Animation.AnimationListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ivLogo?.apply {
            doOnLayout {
                val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.anim_splash)
                startAnimation(animation)
                animation?.setAnimationListener(this@SplashActivity)
            }
        }
    }

    override fun onAnimationEnd(animation: Animation?) {
        openNextScreen()
    }

    private fun openNextScreen() {
        startActivity<MainActivity>()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onAnimationStart(animation: Animation?) {}

    override fun onAnimationRepeat(animation: Animation?) {}
}
