package com.example.networkretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkretrofit.databinding.ItemRecyclerBinding
import com.example.networkretrofit.model.Repository
import com.example.networkretrofit.model.RepositoryItem

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {
    var userList: Repository? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setUser(user: RepositoryItem?) {
            user?.let { repositoryItem ->
                binding.textName.text = repositoryItem.name
                binding.textId.text = repositoryItem.node_id
                val url = repositoryItem.owner.avatar_url

                Glide.with(binding.imageAvatar)
                    .load(url)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_cancel_24)
                    .into(binding.imageAvatar)
            }
        }
    }
}

