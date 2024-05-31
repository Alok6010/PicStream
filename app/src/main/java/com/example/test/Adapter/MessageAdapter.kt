package com.example.test.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.Model.MessageModel
import com.example.test.Model.UserModel
import com.example.test.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageAdapter(val context : Context, val list : List<MessageModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        val MSG_TYPE_RIGHT = 0
        val MSG_TYPE_LEFT = 1
        val MSG_TYPE_RIGHT_IMAGE = 2
        val MSG_TYPE_LEFT_IMAGE = 3
        val MSG_TYPE_RIGHT_GIF = 4
        val MSG_TYPE_LEFT_GIF = 5

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val text = itemView.findViewById<TextView>(R.id.messageText)
        val image = itemView.findViewById<ImageView>(R.id.senderImage)

    }
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.messageImage)
        val senderImage: ImageView = itemView.findViewById(R.id.senderImage)
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return when {
            message.senderId == FirebaseAuth.getInstance().currentUser!!.phoneNumber && message.type == "text" -> MSG_TYPE_RIGHT
            message.senderId == FirebaseAuth.getInstance().currentUser!!.phoneNumber && message.type == "image" -> MSG_TYPE_RIGHT_IMAGE
            message.senderId == FirebaseAuth.getInstance().currentUser!!.phoneNumber && message.type == "gif" -> MSG_TYPE_RIGHT_GIF
            message.type == "text" -> MSG_TYPE_LEFT
            message.type == "image" -> MSG_TYPE_LEFT_IMAGE
            else -> MSG_TYPE_LEFT_GIF
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MSG_TYPE_LEFT -> MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_sender_message, parent, false))
            MSG_TYPE_RIGHT -> MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_receiver_message, parent, false))
            MSG_TYPE_LEFT_IMAGE -> ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_sender_image_message, parent, false))
            MSG_TYPE_RIGHT_IMAGE -> ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_receiver_image_message, parent, false))
            MSG_TYPE_LEFT_GIF -> ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_sender_image_message, parent, false))
            else -> ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_receiver_image_message, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]

        when (holder) {
            is MessageViewHolder -> {
                holder.text.text = message.message
                loadImage(holder.image, message.senderId)
            }
            is ImageViewHolder -> {
                if (message.type == "gif") {
                    Glide.with(context).asGif().load(message.message).into(holder.image)
                } else {
                    Glide.with(context).load(message.message).into(holder.image)
                }
                loadImage(holder.senderImage, message.senderId)
            }
        }
    }

    private fun loadImage(imageView: ImageView, senderId: String?) {
        FirebaseDatabase.getInstance().getReference("users")
            .child(senderId!!).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val data = snapshot.getValue(UserModel::class.java)
                            Glide.with(context).load(data!!.image).placeholder(R.drawable.woman).into(imageView)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
    }

}