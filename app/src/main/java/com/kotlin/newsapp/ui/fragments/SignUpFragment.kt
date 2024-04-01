package com.kotlin.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlin.newsapp.R
import com.kotlin.newsapp.databinding.FragmentSignInBinding
import com.kotlin.newsapp.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_error, null)
        showBottomNavigationBar()

        binding.btnSignUp.setOnClickListener{
            it.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }
    private fun showBottomNavigationBar() {
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            val bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.visibility = View.GONE
        }
    }
}