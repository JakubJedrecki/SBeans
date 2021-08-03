package jakub.jedrecki.ahilt.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakub.jedrecki.ahilt.R
import jakub.jedrecki.ahilt.util.IdResourceString
import jakub.jedrecki.ahilt.util.ResourceString
import jakub.jedrecki.ahilt.util.TextResourceString
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _snackBarMsg: MutableLiveData<ResourceString> = MutableLiveData()
    private val _isAuthenticated: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _usernameError: MutableLiveData<ResourceString> = MutableLiveData(null)
    private val _passwordError: MutableLiveData<ResourceString> = MutableLiveData(null)

    val snackBarMsg: LiveData<ResourceString> = _snackBarMsg
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated
    val usernameError: LiveData<ResourceString> = _usernameError
    val passwordError: LiveData<ResourceString> = _passwordError
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun logIn() {
        if (validateLoginCredentials()) {
            //auth user call
            //_snackBarMsg.postValue() //network error etc
            _isAuthenticated.postValue(true)
        }
    }

    private fun validateLoginCredentials(): Boolean {
        var outcome = true

        _usernameError.value = null
        _passwordError.value = null

        if (username.value.isNullOrBlank()) {
            outcome = false
            _usernameError.postValue(IdResourceString(R.string.error_username_empty))
        } else {
            _usernameError.postValue(null)
        }

        if (password.value.isNullOrBlank()) {
            outcome = false
            _passwordError.postValue(IdResourceString(R.string.error_password_empty))
        } else {
            _passwordError.postValue(null)
        }

        return outcome
    }
}