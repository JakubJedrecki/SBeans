package jakub.jedrecki.ahilt.ui.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jakub.jedrecki.ahilt.R
import jakub.jedrecki.ahilt.domain.model.PhotoItem
import jakub.jedrecki.ahilt.ui.photos.adapter.PhotosAdapter

@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private val photosViewModel: PhotosViewModel by viewModels()

    private lateinit var driversRecyclerView: RecyclerView
    private lateinit var photosAdapter: PhotosAdapter
    private val photosList: MutableList<PhotoItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()

        photosAdapter = PhotosAdapter(photosList)
        val viewManager = LinearLayoutManager(requireContext())
        driversRecyclerView = view.findViewById(R.id.rv_photos)
        driversRecyclerView.apply {
            layoutManager = viewManager
            adapter = photosAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        photosViewModel.getPhotos()
    }

    private fun setUpObservers() {
        photosViewModel.photos.observe(viewLifecycleOwner, { photos ->
            photosList.removeAll(photos)
            photosList.addAll(photos)
            photosAdapter.notifyDataSetChanged()
        })

        photosViewModel.snackBarMsg.observe(viewLifecycleOwner) { source ->
            val message = source.format(requireContext())
            Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
            Log.e("TAG", message)
        }
    }
}