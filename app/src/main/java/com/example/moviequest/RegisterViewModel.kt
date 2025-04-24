package com.example.moviequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _userNoBlankSpaces = MutableLiveData<Boolean>()
    val userNoBlankSpaces: LiveData<Boolean> get() = _userNoBlankSpaces

    fun onUsernameChanged(username: String) {
        _userNoBlankSpaces.value = username.isNotBlank()
    }
}