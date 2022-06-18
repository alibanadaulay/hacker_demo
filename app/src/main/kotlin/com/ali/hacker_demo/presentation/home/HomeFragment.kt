package com.ali.hacker_demo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ali.hacker_demo.R
import com.ali.hacker_demo.presentation.MainViewModel
import com.ali.hacker_demo.common.StateHackerNews
import com.ali.hacker_demo.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mViewModel: MainViewModel by activityViewModels()
    private lateinit var mBinding: FragmentHomeBinding
    private val mItemAdapter: ItemAdapter by lazy {
        ItemAdapter(mViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setItemAdapter()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.viewState.collect { render(it) }
        }
        with(mBinding){
            itemNews.parentItemNew.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.makeStatIdle()
    }

    private fun render(ui: StateHackerNews) {
        with(mBinding) {
            when (ui) {
                is StateHackerNews.GetDetailNew,
                StateHackerNews.NewsOnSelected -> {
                    findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
                }
                StateHackerNews.GetDataOnProgress -> {
                    progressBarFragmentHome.visibility = View.VISIBLE
                }
                is StateHackerNews.GetDataNewHackerSuccess -> {
                    progressBarFragmentHome.visibility = View.GONE
                    mItemAdapter.updateData(ui.hackerNews)
                }
                is StateHackerNews.ShowFavorite -> {
                    val comment = "Comment: ${ui.hackerNews.descendants}"
                    val score = "Score: ${ui.hackerNews.score}"
                    itemNews.titleItemNew.text = ui.hackerNews.title
                    itemNews.jumlahComentarItemNew.text =
                        comment
                    itemNews.scoreItemNew.text = score
                    itemNews.parentItemNew.visibility = if (ui.isShow) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
                is StateHackerNews.OnError -> {
                    progressBarFragmentHome.visibility = View.GONE
                }

            }
        }
    }

    private fun setItemAdapter() {
        with(mBinding) {
            rcvHome.layoutManager = GridLayoutManager(requireContext(), 2)
            rcvHome.adapter = mItemAdapter
        }
    }
}