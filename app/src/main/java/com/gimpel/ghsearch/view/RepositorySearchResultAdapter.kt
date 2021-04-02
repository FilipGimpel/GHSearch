package com.gimpel.ghsearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gimpel.ghsearch.model.Repository
import com.gimpel.ghsearch.R
import kotlinx.android.synthetic.main.item_layout.view.*


class RepositorySearchResultAdapter(
    private val repositories: ArrayList<Repository>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<RepositorySearchResultAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View, private val clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Repository) {
            itemView.textViewRepositoryName.text = repository.name
            itemView.textViewRepositoryUrl.text = repository.html_url
            itemView.container.setOnClickListener {
                clickListener.onItemClick(repository.html_url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            ),
            clickListener
        )

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(repositories[position])

    fun setData(list: List<Repository>) {
        repositories.clear()
        repositories.addAll(list)
    }

    interface OnItemClickListener {
        fun onItemClick(url: String)
    }
}