package com.example.userdirectory_411a.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userdirectory_411a.data.User
import com.example.userdirectory_411a.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private fun loadUsers() {
        viewModelScope.launch {
            repository.getAllUsers().collect { userList ->
                _users.value = userList
            }

        }
    }


    private fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.fetchAndStoreUsers()
            _isLoading.value = false
        }
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                repository.getAllUsers().collect { userList ->
                    _users.value = userList
                }
            } else {
                repository.searchUsers(query).collect { userList ->
                    _users.value = userList
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchUsers(query)
    }


    init {
        fetchUsers()
        loadUsers()
    }


}