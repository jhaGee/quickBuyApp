package com.example.quickbuyapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quickbuyapp.model.repositories.UserRepository


@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory (
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(repository) as T
    }
}