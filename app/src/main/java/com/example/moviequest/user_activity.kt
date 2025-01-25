package com.example.moviequest

import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviequest.adapter.MovieAdapter
import com.google.android.material.navigation.NavigationView

class user_activity : AppCompatActivity() {

    private var isDarkMode = false
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        initRecyclerView()
        setupNavigationDrawer()
        setupThemeToggle()
    }

    private fun initRecyclerView() {
        val rvUser = findViewById<RecyclerView>(R.id.userRecycler)
        rvUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvUser.adapter = MovieAdapter(MovieProvider.movieList)
    }

    private fun setupNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        val openNavButton: ImageButton = findViewById(R.id.open_nav_button)

        openNavButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_info -> showInfoToast()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupThemeToggle() {
        val themeToggle: ImageView = findViewById(R.id.theme_toggle)
        themeToggle.setImageResource(
            if (isDarkMode) R.drawable.toggle_dark
            else R.drawable.toggle_sun
        )

        themeToggle.setOnClickListener {
            isDarkMode = !isDarkMode
            applyTheme(isDarkMode)
            themeToggle.setImageResource(
                if (isDarkMode) R.drawable.toggle_dark
                else R.drawable.toggle_sun
            )
        }
    }

    private fun applyTheme(darkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_parties, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_partie -> showItems(4)
            R.id.nav_friend -> showItems(3)
        }
        return super.onContextItemSelected(item)
    }

    private fun showItems(count: Int) {
        val messages = listOf("Item 1", "Item 2").take(count)
        messages.forEach { showToast(it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showInfoToast() {
        Toast.makeText(
            this,
            "Aquesta aplicació està dissenyada per als apassionats del cinema.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isDarkMode", isDarkMode)
    }
}