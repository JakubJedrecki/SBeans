package jakub.jedrecki.ahilt.ui.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.mockk
import jakub.jedrecki.ahilt.util.getOrAwaitValue
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        loginViewModel = LoginViewModel()
    }

    @Test
    fun `log in empty username`() {
        loginViewModel.password.postValue("pw")

        loginViewModel.logIn()

        val isAuthenticated = loginViewModel.isAuthenticated.getOrAwaitValue()
        val error = loginViewModel.usernameError.getOrAwaitValue()

        Truth.assertThat(isAuthenticated).isFalse()
        Truth.assertThat(error).isNotNull()
    }

    @Test
    fun `log in empty password`() {
        loginViewModel.username.postValue("username")

        loginViewModel.logIn()

        val isAuthenticated = loginViewModel.isAuthenticated.getOrAwaitValue()
        val error = loginViewModel.passwordError.getOrAwaitValue()

        Truth.assertThat(isAuthenticated).isFalse()
        Truth.assertThat(error).isNotNull()
    }

    @Test
    fun `log in success`() {
        loginViewModel.username.postValue("username")
        loginViewModel.password.postValue("pw")

        loginViewModel.logIn()

        val isAuthenticated = loginViewModel.isAuthenticated.getOrAwaitValue()
        val pwError = loginViewModel.passwordError.getOrAwaitValue()
        val usernameError = loginViewModel.passwordError.getOrAwaitValue()

        Truth.assertThat(isAuthenticated).isTrue()
        Truth.assertThat(pwError).isNull()
        Truth.assertThat(usernameError).isNull()
    }
}