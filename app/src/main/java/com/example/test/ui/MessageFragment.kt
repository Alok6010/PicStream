package com.example.test.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.test.Adapter.MessageUserAdapter
import com.example.test.R
import com.example.test.databinding.FragmentMessageBinding
import com.example.test.ui.DatingFragment.Companion
import com.example.test.ui.DatingFragment.Companion.list
import com.example.test.utlis.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageFragment : Fragment() {


    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(layoutInflater)

         getData()

        return binding.root
    }

    private fun getData() {

        Config.showDialog(requireContext())


        val currrentId = FirebaseAuth.getInstance().currentUser!!.phoneNumber

        FirebaseDatabase.getInstance().getReference("Chats")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    var list = arrayListOf<String>()
                    var newList = arrayListOf<String>()


                    for (data in snapshot.children){
                        if (data.key!!.contains(currrentId!!)){
                            list.add(data.key!!.replace(currrentId,""))
                            newList.add(data.key!!)

                        }
                    }


                    // Debugging: Check if the correct data is retrieved
                    Log.d("MessageFragment", "List: $list, NewList: $newList")

                    binding.recyclerView.adapter = MessageUserAdapter(requireContext(),list, newList)


                    Config.hideDialog()
                }

                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }



}