package com.peterlaurence.book.javatokotlin.part1

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.peterlaurence.book.javatokotlin.part1.fragments.Chap2Fragment
import com.peterlaurence.book.javatokotlin.part1.fragments.java.JavaFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val fragmentTags = listOf(JAVA_TAG, CHAP2_TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null) {
            hideWelcomeMsg()
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            removeFragments("")
            showWelcomeMsg()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sample1 -> showFragment(JAVA_TAG) {
                JavaFragment.newInstance("a user")
            }
            R.id.nav_sample2 -> showFragment(CHAP2_TAG) {
                Chap2Fragment()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFragment(tag: String, onCreate: () -> Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        removeFragments(tag)
        hideWelcomeMsg()
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: {
            val f = onCreate()
            transaction.add(R.id.content_frame, f, tag)
            f
        }()
        transaction.show(fragment)
        transaction.commit()
    }

    private fun removeFragments(tagExcept: String) {
        val transaction = supportFragmentManager.beginTransaction()
        for (tag in fragmentTags) {
            if (tag == tagExcept) continue
            supportFragmentManager.findFragmentByTag(tag)?.also {
                transaction.remove(it)
            }
        }
        transaction.commit()
    }

    private fun hideWelcomeMsg() {
        val msg: TextView = findViewById(R.id.welcome_text)
        msg.visibility = View.GONE
    }

    private fun showWelcomeMsg() {
        val msg: TextView = findViewById(R.id.welcome_text)
        msg.visibility = View.VISIBLE
    }
}

const val JAVA_TAG = "java"
const val CHAP2_TAG = "chap2"
