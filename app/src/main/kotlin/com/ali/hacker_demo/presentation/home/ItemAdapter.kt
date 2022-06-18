package com.ali.hacker_demo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ali.hacker_demo.common.model.NewsCache
import com.ali.hacker_demo.databinding.ItemNewBinding
import com.ali.hacker_demo.presentation.MainViewModel

class ItemAdapter(private val vm: MainViewModel) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val itemHackers: MutableList<NewsCache> = mutableListOf()

    class ViewHolder(private val view: ItemNewBinding) : RecyclerView.ViewHolder(view.root) {
        fun setData(newsCache: NewsCache) {
            val comment = "Comment: ${newsCache.descendants}"
            val score = "Score: ${newsCache.score}"
            with(view) {
                titleItemNew.text = newsCache.title
                jumlahComentarItemNew.text = comment
                scoreItemNew.text = score
            }
        }
    }

    fun updateData(list: List<NewsCache>) {
        itemHackers.clear()
        itemHackers.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemHackers[position]
        holder.setData(item)
        holder.itemView.setOnClickListener {
            vm.getNews(item)
        }
    }

    override fun getItemCount(): Int = itemHackers.size
}