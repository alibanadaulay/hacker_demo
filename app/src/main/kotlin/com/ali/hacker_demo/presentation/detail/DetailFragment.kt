package com.ali.hacker_demo.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.hacker_demo.R
import com.ali.hacker_demo.common.StateHackerNews
import com.ali.hacker_demo.databinding.FragmentDetailBinding
import com.ali.hacker_demo.presentation.MainViewModel

class DetailFragment : Fragment() {

    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var mBinding: FragmentDetailBinding
    private val mDetailCommentAdapter: DetailCommentAdapter by lazy {
        DetailCommentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.viewState.collect { render(it) }
        }
        with(mBinding) {
            favoriteFragmentDetail.setOnClickListener {
                mViewModel.saveFavorite { bool ->
                    setStartIconFavorite(bool, it as ImageView)
                }
            }
        }
    }

    private fun setStartIconFavorite(bool: Boolean, view: ImageView) {
        view.setImageResource(
            if (bool) {
                R.drawable.ic_baseline_star_rate_24
            } else {
                R.drawable.ic_baseline_star_outline
            }
        )
    }

    private fun render(ui: StateHackerNews) {
        with(mBinding) {
            when (ui) {
                is StateHackerNews.GetDetailNew -> {
                    val item = ui.newsDetailCache
                    val title = "${item.type}: ${item.title}"
                    val author = "By ${item.author} \n ${item.date}"
                    titleFragmentDetail.text = title
                    dateFragmentDetail.text = author
                    descriptionFragmentDetail.text = item.description
                    if (item.isCommentsIsLoaded) {
                        mDetailCommentAdapter.setComments(item.comments)
                    }
                    setStartIconFavorite(ui.isFavorite, favoriteFragmentDetail)
                }
                is StateHackerNews.OnError -> {

                }
                else -> {

                }
            }
        }
    }

    private fun initRecyclerView() {
        with(mBinding) {
            detailRcv.layoutManager = LinearLayoutManager(requireContext())
            detailRcv.adapter = mDetailCommentAdapter
        }
    }
}