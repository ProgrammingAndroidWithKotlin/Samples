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
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.peterlaurence.book.javatokotlin.part1.fragments.*
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
            R.id.nav_sample1 -> createAndShowFragment(JAVA_TAG, JavaFragment::class.java)
            R.id.nav_sample2 -> createAndShowFragment(CHAP2_TAG, Chap2Fragment::class.java)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun <T : Fragment> createAndShowFragment(tag: String, clazz: Class<T>) {
        showFragment(tag) { tr, _ ->
            createFragment(tr, clazz, tag)
        }
    }

    private fun showFragment(tag: String, onCreate: (t: FragmentTransaction, tag: String) -> Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        removeFragments(tag)
        hideWelcomeMsg()
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: onCreate(transaction, tag)
        transaction.show(fragment)
        transaction.commit()
    }

    private fun <T : Fragment> createFragment(transaction: FragmentTransaction, clazz: Class<T>, tag: String): Fragment {
        val f = clazz.newInstance()
        transaction.add(R.id.content_frame, f, tag)
        return f
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
