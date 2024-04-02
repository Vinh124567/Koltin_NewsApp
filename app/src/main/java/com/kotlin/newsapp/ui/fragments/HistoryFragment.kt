package com.kotlin.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kotlin.newsapp.R
import com.kotlin.newsapp.adapters.HistoryAdapter
import com.kotlin.newsapp.adapters.NewsAdapter
import com.kotlin.newsapp.databinding.FragmentFavoriteBinding
import com.kotlin.newsapp.databinding.FragmentHistoryBinding
import com.kotlin.newsapp.ui.NewsActivity
import com.kotlin.newsapp.ui.NewsViewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    lateinit var newsViewModel: NewsViewModel
    lateinit var historyAdapter: HistoryAdapter
    private lateinit var noItemCard: CardView
    private lateinit var errorText: TextView
    private lateinit var binding: FragmentHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        noItemCard = view.findViewById(R.id.noItem)

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.no_item, null)

        errorText = view.findViewById(R.id.errorText)

        setupFavoritesRecycler()

        historyAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_historyFragment_to_articleFragment, bundle)
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
                val article = historyAdapter.differ.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(view, "Remove Success", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        newsViewModel.addNewsToFavorites(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView((binding.recyclerFavourites))
        }

        newsViewModel.loadHistory()
        newsViewModel.articleHistory.observe(viewLifecycleOwner) { articles ->
            historyAdapter.differ.submitList(articles)
            if (articles.isNotEmpty()) {
                hideErrorMessage()
            } else {
                showErrorMessage("No data")
            }
        }
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
        historyAdapter = HistoryAdapter()
        binding.recyclerFavourites.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}




