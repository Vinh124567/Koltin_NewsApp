package com.kotlin.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kotlin.newsapp.R
import com.kotlin.newsapp.databinding.FragmentArticleBinding
import com.kotlin.newsapp.databinding.FragmentEdtArticleBinding
import com.kotlin.newsapp.models.Article
import com.kotlin.newsapp.models.Source
import com.kotlin.newsapp.ui.NewsActivity
import com.kotlin.newsapp.ui.NewsViewModel

class edtArticleFragment : Fragment(R.layout.fragment_edt_article) {
    private lateinit var newsViewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentEdtArticleBinding
    private lateinit var titleText: TextView
    private lateinit var sourceText: TextView
    private lateinit var dateTimeText: TextView
    private lateinit var contentText: TextView
    private lateinit var authorText: TextView
    private lateinit var articleImage: ImageView
    private lateinit var bookmarkButton: ImageButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEdtArticleBinding.bind(view)

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var article = args.article
        setContent(args.article)
        newsViewModel = (activity as NewsActivity).newsViewModel
        binding.btnUpdate.setOnClickListener{
            val author = binding.Author.text.toString()
            val content = binding.Content.text.toString()
            val title = binding.Title.text.toString()
            val description = binding.Desc.text.toString()
            val sourceName = binding.Source.text.toString()
            val url = binding.Url.text.toString()
            val urlToImage = binding.UrlToImage.text.toString()

            val updatedArticle = Article(
                author = author,
                content = content,
                title = title,
                description = description,
                source = Source(name = sourceName),
                url = url,
                urlToImage = urlToImage
            )

            newsViewModel.update(article.id!!, updatedArticle)
        }

    }

    private fun setContent(article: Article) {
        binding.Author.setText(article.author)
        binding.Content.setText(article.content)
        binding.Title.setText(article.title)
        binding.Desc.setText(article.description)
        binding.Source.setText(article.source?.name)
        binding.Url.setText(article.url)
        binding.UrlToImage.setText(article.urlToImage)

    }

}