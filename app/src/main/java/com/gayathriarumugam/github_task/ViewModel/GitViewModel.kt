package com.gayathriarumugam.github_task.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.gayathriarumugam.github_task.Data.Model.GithubRepo
import com.gayathriarumugam.github_task.Data.Model.SearchResponse
import com.gayathriarumugam.github_task.Data.Repo.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitViewModel(application: Application) : AndroidViewModel(application) {

    internal var application: Application = application
    var searchList: MutableLiveData<List<GithubRepo>>? = null

    fun fetchRepoList(searchString: String): MutableLiveData<List<GithubRepo>>? {
        viewModelScope.launch(Dispatchers.IO) {
            searchList = AppRepo.instance.getNetworkResponse(application = application, searchString = searchString)
        }
        return searchList
    }
}