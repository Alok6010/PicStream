package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.test.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener(this)

        val navController = findNavController(R.id.fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite -> {
                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, FavoriteActivity::class.java))

            }
            R.id.rateus -> {
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show()
                showRateDialog()

            }
            R.id.termsAndConditions -> {
                Toast.makeText(this, "Terms And Conditions", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, TnCActivity::class.java))


            }
            R.id.shareApp -> {
                Toast.makeText(this, "shareApp", Toast.LENGTH_SHORT).show()
                showShareDialog()

            }
            R.id.privacypolicy -> {
                Toast.makeText(this, "Privacy Policy ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PrivacyPolicy::class.java))

            }
            R.id.developer -> {
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Developer::class.java))

            }
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle!!.onOptionsItemSelected(item))
            true
        else
            super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.close()
        }else
            super.onBackPressed()
    }

    private fun showRateDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Rate Us")
        builder.setMessage("Thank you for your interest in rating our app. Currently, our app is not available on the Play Store.")
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun showShareDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Share App")
        builder.setMessage("Thank you for your interest for Sharing our app. Currently, our app is not available on the Play Store.")
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

}