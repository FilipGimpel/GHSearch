package com.gimpel.ghsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimpel.ghsearch.model.Repository
import com.gimpel.ghsearch.networking.GitHubApi
import com.gimpel.ghsearch.util.Resource
import com.gimpel.ghsearch.util.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val gitHubApi: GitHubApi
) : ViewModel() {

    val searchResult : LiveData<Resource<List<Repository>>> by lazy {
        SingleLiveEvent<Resource<List<Repository>>>()
    }

    val searchQuery : MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    fun performGithubSearch(query : String) {
        viewModelScope.launch {
            (searchResult as? MutableLiveData)?.postValue(Resource.loading())
            gitHubApi.listRepos(query).let {
                if (it.isSuccessful) {
                    (searchResult as? MutableLiveData)?.postValue(Resource.success(it.body()?.items))
                } else {
                    (searchResult as? MutableLiveData)?.postValue(Resource.error())
                }
            }
        }
    }
}