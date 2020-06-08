package com.gayathriarumugam.github_task.View

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gayathriarumugam.github_task.Data.Model.GithubRepo
import com.gayathriarumugam.github_task.R
import com.gayathriarumugam.github_task.View.Adapter.RepoListAdapter
import com.gayathriarumugam.github_task.ViewModel.GitViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: GitViewModel
    private lateinit var repoList: List<GithubRepo>
    private lateinit var repoListAdapter: RepoListAdapter
    private var searchText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.frag_list_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(requireActivity()).get(GitViewModel::class.java)

        return view
    }

    override fun onStart() {
        super.onStart()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(requireView().context)
        repoListAdapter = RepoListAdapter(requireContext(), viewModel)
        recyclerView.adapter = repoListAdapter

        initCall()
    }

    fun initCall () {
        viewModel.fetchRepoList(searchText)
        viewModel.searchList?.observe(viewLifecycleOwner, Observer(function = fun(searchList: List<GithubRepo>?) {
            searchList?.let {
                repoList = searchList
                repoListAdapter.repoList = repoList
                repoListAdapter.notifyDataSetChanged()
                setOnItemClick(repoList)
            }
        }))
    }

    //Call when item clicked
    fun setOnItemClick(gitHubRepo: List<GithubRepo>) {
        repoListAdapter.setItemClickListener(object :
            RepoListAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val newFragment = DetailsFragment(gitHubRepo.get(position))
                fragmentManager!!.beginTransaction()
                    .replace(R.id.frag_container, newFragment)
                    .addToBackStack("attach")
                    .commit()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_view, menu)

        val searchItem = menu.findItem(R.id.action_search)
        if(searchItem != null) {
            val searchView: SearchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query!!.isNotEmpty()) {
                        searchText = query
                        if(searchText.contains(" ")) {
                            searchText.replace(" ", "+")
                        }
                        initCall()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.action_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}