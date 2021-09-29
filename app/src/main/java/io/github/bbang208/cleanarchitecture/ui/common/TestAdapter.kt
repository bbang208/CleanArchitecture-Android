package io.github.bbang208.cleanarchitecture.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import io.github.bbang208.cleanarchitecture.AppExecutors
import io.github.bbang208.cleanarchitecture.MainViewModel
import io.github.bbang208.cleanarchitecture.R
import io.github.bbang208.cleanarchitecture.data.models.Repo
import io.github.bbang208.cleanarchitecture.databinding.ItemRepoBinding

class TestAdapter(
    appExecutors: AppExecutors,
    private val viewModel: MainViewModel
) : DataBoundListAdapter<Repo, ItemRepoBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.owner == newItem.owner
                    && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.description == newItem.description
                    && oldItem.stars == newItem.stars
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ItemRepoBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_repo,
            parent,
            false
        )
    }

    override fun bind(binding: ItemRepoBinding, item: Repo) {
        binding.item = item
        binding.viewModel = viewModel
    }

}