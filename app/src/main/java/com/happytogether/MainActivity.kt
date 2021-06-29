package com.happytogether

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.happytogether.data.EXTRA_DATA
import com.happytogether.data.FirstRunSharePref
import com.happytogether.databinding.ActivityMainBinding
import com.happytogether.intro.SplashActivity
import com.happytogether.ui.CartFragment
import com.happytogether.ui.HomeFragment
import com.happytogether.ui.MessagesFragment
import com.happytogether.ui.ProfileFragment

class MainActivity : AppCompatActivity() {

    var myFirstRun : FirstRunSharePref? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                var d = intent.getStringExtra(EXTRA_DATA)
                val myBundle= Bundle()
                myBundle.putString(EXTRA_DATA, d)
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.container, args = myBundle)
            }
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener)
    }

    private fun init() {
        myFirstRun = FirstRunSharePref(this)
        if (myFirstRun!!.firstRun) {
//            myFirstRun!!.firstRun = false
            val firstIntent = Intent(this, SplashActivity::class.java)
            startActivity(firstIntent)
            finish()
        }
    }

    private val mNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item -> when (item.itemId) {
        R.id.nav_home -> {
            val homeFragment = HomeFragment()
            openFragment(homeFragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.nav_cart -> {
            val cartFragment = CartFragment()
            openFragment(cartFragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.nav_messages -> {
            val messagesFragment = MessagesFragment()
            openFragment(messagesFragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.nav_profile -> {
            val profileFragment = ProfileFragment()
            openFragment(profileFragment)
            return@OnNavigationItemSelectedListener true
        }
    }
        false
    }

    private fun openFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                var d = intent.getStringExtra(EXTRA_DATA)
                val myBundle= Bundle()
                myBundle.putString(EXTRA_DATA, d)
                fragment.arguments = myBundle
                replace(R.id.container, fragment)
                addToBackStack(null)
                commit()
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}