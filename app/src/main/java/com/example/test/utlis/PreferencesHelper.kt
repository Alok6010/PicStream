package com.example.test.utlis

import android.content.Context
import com.example.test.Model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object PreferencesHelper {

    private const val PREF_NAME = "favorites"
    private const val FAVORITE_USERS_KEY = "favorite_users"

    fun saveFavoriteUser(context: Context, userModel: UserModel) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = sharedPref.getString(FAVORITE_USERS_KEY, null)
        val type = object : TypeToken<ArrayList<UserModel>>() {}.type
        val favoritesList: ArrayList<UserModel> = if (!json.isNullOrEmpty()) {
            gson.fromJson(json, type)
        } else {
            ArrayList()
        }

        favoritesList.add(userModel)
        val updatedJson = gson.toJson(favoritesList)
        editor.putString(FAVORITE_USERS_KEY, updatedJson)
        editor.apply()
    }

    fun getFavoriteUsers(context: Context): ArrayList<UserModel> {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString(FAVORITE_USERS_KEY, null)
        val type = object : TypeToken<ArrayList<UserModel>>() {}.type
        return if (!json.isNullOrEmpty()) {
            gson.fromJson(json, type)
        } else {
            ArrayList()
        }
    }
}