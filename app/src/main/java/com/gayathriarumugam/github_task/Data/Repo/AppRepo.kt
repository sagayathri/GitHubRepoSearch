package com.gayathriarumugam.github_task.Data.Repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gayathriarumugam.github_task.Data.Model.GithubRepo
import com.gayathriarumugam.github_task.Data.Model.SearchResponse
import com.gayathriarumugam.github_task.Data.Network.API
import com.gayathriarumugam.github_task.Utils.Constant
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRepo() {
    val constant = Constant()
    var searchResponse: SearchResponse? = null
    var listOfResponse = MutableLiveData<List<GithubRepo>>()

    //Builds and create Retrofit
    private fun retrofit(): API {
        return Retrofit.Builder()
            .baseUrl(constant.base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    companion object {
        private var sharedInstance: AppRepo? = null
        //Creates a shared instance of RetrofitClient class
        val instance: AppRepo
            @Synchronized get() {
                if (sharedInstance == null) {
                    sharedInstance = AppRepo()
                }
                return sharedInstance as AppRepo
            }
    }

    //Makes a network call in a thread and suspend it once it completed
    fun getNetworkResponse(application: Application, searchString: String): MutableLiveData<List<GithubRepo>>? {
        retrofit().getRepos(searchString).enqueue(object : retrofit2.Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.body() != null) {
                    searchResponse = response.body()
                    listOfResponse.postValue(searchResponse?.items)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("Failure: ", t.localizedMessage)
            }
        })
        return listOfResponse
    }
}