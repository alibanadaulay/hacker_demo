package com.ali.hacker_demo.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ali.hacker_demo.databinding.ItemCommentBinding

class DetailCommentAdapter : RecyclerView.Adapter<DetailCommentAdapter.ViewHolder>() {
    private val comments: MutableList<String> = mutableListOf()

    class ViewHolder(private val view: ItemCommentBinding) : RecyclerView.ViewHolder(view.root) {
        fun setComments(comment: String) {
            with(view) {
                tvComment.text = comment
            }
        }
    }

    fun setComments(list: List<String>){
        comments.clear()
        comments.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setComments(comments[position])
    }

    override fun getItemCount(): Int = comments.size
}