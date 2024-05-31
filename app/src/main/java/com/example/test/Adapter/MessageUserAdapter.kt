package com.example.test.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.ChatActivity
import com.example.test.Model.UserModel
import com.example.test.databinding.UserItemLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageUserAdapter(
    val context: Context,
    val list: ArrayList<String>,
    val chatKey: List<String>

):RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {

    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
        return MessageUserViewHolder(UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {

        FirebaseDatabase.getInstance().getReference("users")
            .child(list[position]).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val data = snapshot.getValue(UserModel::class.java)

                            Glide.with(context).load(data!!.image).into(holder.binding.userImage)

                            holder.binding.userName.text = data.name

                            // Pass userName along with userId and chatId
                            holder.itemView.setOnClickListener {
                                val intent = Intent(context, ChatActivity::class.java)
                                intent.putExtra("chat_Id", chatKey[position])
                                intent.putExtra("userId", list[position])
                                intent.putExtra("userName", data.name) // add userName
                                context.startActivity(intent)
                            }


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }

                }
            )

//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, ChatActivity::class.java)
//            intent.putExtra("chat_Id", chatKey[position])
//            intent.putExtra("userId", list[position])
//            context.startActivity(intent)
//        }




    }




}