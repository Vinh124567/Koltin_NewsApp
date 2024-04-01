package com.kotlin.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlin.newsapp.R
import com.kotlin.newsapp.databinding.FragmentHeadlinesBinding
import com.kotlin.newsapp.databinding.FragmentSignInBinding
import com.kotlin.newsapp.ui.NewsActivity
import com.kotlin.newsapp.ui.NewsViewModel


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: FragmentSignInBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_error, null)

        newsViewModel = (activity as NewsActivity).newsViewModel
        showBottomNavigationBar()

        binding.btnSignIn.setOnClickListener{view->
            val userName = binding.emailEt.text.toString()
            val userPassword = binding.passET.text.toString()
            if (userName.isNotEmpty() || userPassword.isNotEmpty()){
                newsViewModel.loginWithEmail(userName, userPassword) { isLogin ->
                    if (isLogin) {
                        Toast.makeText(
                            requireActivity(),
                            "Đăng nhập thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        view.findNavController().navigate(R.id.action_signInFragment_to_headlinesFragment)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "tài khoản hoặc mật khẩu không chính xác",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                Toast.makeText(
                    requireActivity(),
                    "Vui lòng điền đủ thông tin",
                    Toast.LENGTH_SHORT).show()
            }
        }
        binding.textView.setOnClickListener{
            it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
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