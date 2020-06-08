package com.gayathriarumugam.github_task.View

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.gayathriarumugam.github_task.Data.Model.GithubRepo
import com.gayathriarumugam.github_task.R
import com.gayathriarumugam.github_task.ViewModel.GitViewModel
import java.time.format.DateTimeFormatter

class DetailsFragment(private val selectedRepo: GithubRepo) : Fragment() {

    private lateinit var viewModel: GitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.frag_details_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Repo Details"

        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                fragmentManager?.popBackStack("attach", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        })

        viewModel = ViewModelProviders.of(requireActivity()).get(GitViewModel::class.java)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoName = view.findViewById<TextView>(R.id.tvRepoName)
        val ownedBy = view.findViewById<TextView>(R.id.tvOwnerName)
        val createdOn = view.findViewById<TextView>(R.id.tvCreatedOn)
        val description = view.findViewById<TextView>(R.id.tvDescription)
        val language = view.findViewById<TextView>(R.id.tvLanguage)
        val forkCount = view.findViewById<TextView>(R.id.tvForkCount)
        val openIssuesCount = view.findViewById<TextView>(R.id.tvOpenIssuesCount)
        val repoURL = view.findViewById<TextView>(R.id.tvRepoURL)

        repoName.text = selectedRepo.name
        ownedBy.text = selectedRepo.owner.login
        val createdAt = selectedRepo.createdAt
        if (createdAt != null) {
            createdOn.text = getReadableDateAndTime(createdAt)
        }
        else {
            createdOn.text = "null"
        }
        val descriptionText = selectedRepo.description
        if (descriptionText != null) {
            description.text = getReadableDateAndTime(descriptionText)
        }
        else {
            description.text = "null"
        }
        language.text = selectedRepo.language
        forkCount.text = selectedRepo.forksCount.toString()
        openIssuesCount.text = selectedRepo.openIssuesCount.toString()
        repoURL.text = selectedRepo.url
    }

    @SuppressLint("NewApi")
    fun getReadableDateAndTime(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val formattedDate = dateString.format(formatter)
        return formattedDate
    }
}