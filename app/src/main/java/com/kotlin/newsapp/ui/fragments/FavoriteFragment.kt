package com.kotlin.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kotlin.newsapp.R
import com.kotlin.newsapp.adapters.NewsAdapter
import com.kotlin.newsapp.databinding.FragmentFavoriteBinding
import com.kotlin.newsapp.ui.NewsActivity
import com.kotlin.newsapp.ui.NewsViewModel


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var noItemCard: CardView
    private lateinit var errorText: TextView
    private lateinit var binding: FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        noItemCard = view.findViewById(R.id.noItem)

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.no_item, null)

        errorText = view.findViewById(R.id.errorText)

        setupFavoritesRecycler()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_favoritesFragment_to_articleFragment, bundle)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]

                Snackbar.make(view, "Remove Success", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {

                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView((binding.recyclerFavourites))
        }


        newsViewModel.articleFavorite.observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
            if (articles.isNotEmpty()) {
                hideErrorMessage()
            } else {
                showErrorMessage("No data")
            }
        }
//        newsViewModel.getFavoriteNews().observe(viewLifecycleOwner) { articles ->
//            newsAdapter.differ.submitList(articles)
//            if (articles.isNotEmpty()) {
//                hideErrorMessage()
//            } else {
//                showErrorMessage("No data")
//            }
//        }
    }

    private var isError = false

    private fun hideErrorMessage() {
        noItemCard.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        noItemCard.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }

    private fun setupFavoritesRecycler() {
        newsAdapter = NewsAdapter()
        binding.recyclerFavourites.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}













