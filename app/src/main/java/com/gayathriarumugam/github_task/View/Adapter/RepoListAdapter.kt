package com.gayathriarumugam.github_task.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gayathriarumugam.github_task.Data.Model.GithubRepo
import com.gayathriarumugam.github_task.R
import com.gayathriarumugam.github_task.ViewModel.GitViewModel

class RepoListAdapter(private val context: Context, private val companyViewModel: GitViewModel) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    private var onItemClickListener: ItemClickListener? = null
    var repoList: List<GithubRepo> = listOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.row_item, viewGroup, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(viewHolder.itemView, position)
        }

        viewHolder.name?.text = repoList[position].name
        viewHolder.language?.text = repoList[position].language
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvName)
        val language = itemView.findViewById<TextView>(R.id.tvLanguage)
    }

    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
