package jakub.jedrecki.ahilt.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jakub.jedrecki.ahilt.R
import jakub.jedrecki.ahilt.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding!!.root

        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.viewmodel = loginViewModel

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        loginViewModel.isAuthenticated.observe(viewLifecycleOwner, { isAuthenticated ->
            if (isAuthenticated) {
                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment_to_photosFragment)
            }
        })

        loginViewModel.snackBarMsg.observe(viewLifecycleOwner, { source ->
            val message = source.format(requireContext())
            Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
            Log.e("TAG", message)
        })
    }
}