package com.example.test.Adapter

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.ChatActivity
import com.example.test.Model.UserModel
import com.example.test.databinding.ItemUserLayoutBinding
import com.example.test.utlis.PreferencesHelper

class DatingAdapter(val context : Context , val list: ArrayList<UserModel>) : RecyclerView.Adapter<DatingAdapter.DatingViewHolder>() {
    inner class DatingViewHolder (val binding : ItemUserLayoutBinding)
        :   RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {

      return DatingViewHolder(ItemUserLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {

        val user = list[position]

        holder.binding.textView5.text= list[position].name
        holder.binding.textView4.text= list[position].email
        Glide.with(context).load(list[position].image).into(holder.binding.userImage)

        holder.binding.chat.setOnClickListener() {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("userId",list[position].number)
            intent.putExtra("userName", list[position].name) // add userName
            context.startActivity(intent)
        }


        // Update the favorite icon and animation based on user.isFavorite
        if (user.isFavorite) {
            holder.binding.heartAnimation.visibility = View.VISIBLE
            holder.binding.heartAnimation.frame = holder.binding.heartAnimation.maxFrame.toInt()
        } else {
            holder.binding.heartAnimation.visibility = View.GONE
        }

        holder.binding.favorite.setOnClickListener {
            user.isFavorite = !user.isFavorite
            PreferencesHelper.saveFavoriteUser(context, user)
            if (user.isFavorite) {
                Toast.makeText(context, "Saved to Favorites", Toast.LENGTH_SHORT).show()
                holder.binding.heartAnimation.visibility = View.VISIBLE
                holder.binding.heartAnimation.playAnimation()
                holder.binding.heartAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        holder.binding.heartAnimation.visibility = View.GONE
                    }
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            } else {
                Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show()
                holder.binding.heartAnimation.visibility = View.GONE
            }
            notifyItemChanged(position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }


}