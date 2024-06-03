package com.example.test.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.test.R
import com.example.test.Model.UserModel
import com.example.test.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       // Config.showDialog(requireContext())

        binding = FragmentProfileBinding.inflate(layoutInflater)

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!).get()
            .addOnSuccessListener {
                if (it.exists()){
                    val data = it.getValue(UserModel::class.java)

                    binding.profileName.setText(data!!.name.toString())
                    binding.profileGender.setText(data.gender.toString())
                    binding.profileEmail.setText(data.email.toString())
                    binding.profileCity.setText(data.city.toString())
                    binding.profileNumber.setText(data.number.toString())

                    val img = data.image.toString()
                    Glide.with(requireContext()).load(img).placeholder(R.drawable.woman)
                        .into(binding.userImage)

                    //Config.hideDialog()
                }
            }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().finish()
        }

//        binding.edtprofile.setOnClickListener {
//          // startActivity(Intent(requireContext(), EditProfileActivity::class.java))
//        }


       return binding.root
    }





}