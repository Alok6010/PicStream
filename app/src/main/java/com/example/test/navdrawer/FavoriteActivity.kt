package com.example.test.navdrawer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.test.Adapter.DatingAdapter
import com.example.test.Model.UserModel
import com.example.test.R
import com.example.test.databinding.ActivityFavoriteBinding
import com.example.test.utlis.PreferencesHelper

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: DatingAdapter
    private var favoritesList: ArrayList<UserModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load favorites from SharedPreferences using PreferencesHelper
        favoritesList = PreferencesHelper.getFavoriteUsers(this)

        adapter = DatingAdapter(this, favoritesList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = adapter

        //code to show one ss image at a time when we swipe ,completely goes to second activity
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

    }
}