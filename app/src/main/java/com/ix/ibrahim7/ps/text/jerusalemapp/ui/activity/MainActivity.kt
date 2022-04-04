package com.ix.ibrahim7.ps.text.jerusalemapp.ui.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.util.*

class MainActivity : AppCompatActivity() {

    private var navHostFragment: Fragment? = null
    private lateinit var mBinding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setLanguage("en", this)
        setContentView(mBinding.root)
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host_home)

        val navController = navHostFragment!!.findNavController()

        NavigationUI.setupWithNavController(
            mBinding.bottomNavigation,
            navController
        )

        /*mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
             when (item.itemId) {
                 R.id.profileFragment2 -> {
                     navController.navigate(R.id.profileFragment2, null, getNavOptions())
                 }
                 else -> {
                     navController.navigate(item.itemId, null, null)
                 }
             }
             true
         }*/

        navHostFragment!!.findNavController()
            .addOnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
                when (destination.id) {
                    R.id.splashFragment -> {
                        window.apply {
                            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                        mBinding.bottomNavigation.visibility = View.GONE
                    }
                    R.id.welcomeFragment -> {
                        window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                        }
                    }
                    R.id.homeFragment2, R.id.moreFragment, R.id.profileFragment2, R.id.newsFragment -> {
                        mBinding.bottomNavigation.visibility = View.VISIBLE
                    }
                    else -> {
                        mBinding.bottomNavigation.visibility = View.GONE
                    }
                }
            }
        Log.e("eee sha1", String().sha1)
    }

    val String.sha1: String
        get() {
            val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }

    private fun getNavOptions(): NavOptions? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_up)
            .setExitAnim(R.anim.slide_down)
            .setPopEnterAnim(R.anim.slide_up)
            .setPopExitAnim(R.anim.slide_down)
            .build()
    }

    companion object {
        fun setLanguage(lan: String, context: Context) {
            val res = context.resources
            val dr = res.displayMetrics
            val cr = res.configuration
            cr.setLocale(Locale(lan))
            res.updateConfiguration(cr, dr)
        }
    }
}
