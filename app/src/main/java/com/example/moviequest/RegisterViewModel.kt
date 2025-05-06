package com.example.moviequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _noBlankSpaces = MutableLiveData<Boolean>()
    val noBlankSpaces: LiveData<Boolean> get() = _noBlankSpaces

    fun onUsernameChanged(username: String) {
        if(username.isNotBlank()){
            _noBlankSpaces.value =true
        }else{_noBlankSpaces.value =false}
        if(username.equals("hola")){
            _noBlankSpaces.value =true
        }else{_noBlankSpaces.value =false}
    }
}