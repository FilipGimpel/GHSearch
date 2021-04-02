package com.gimpel.ghsearch.koin

import com.gimpel.ghsearch.networking.GitHubApi
import com.gimpel.ghsearch.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    viewModel { MainViewModel(get()) }
    single { Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/")
        .build().create(GitHubApi::class.java) }
}