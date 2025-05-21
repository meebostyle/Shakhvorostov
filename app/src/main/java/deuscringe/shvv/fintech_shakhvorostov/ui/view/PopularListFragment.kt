package deuscringe.shvv.fintech_shakhvorostov.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import deuscringe.shvv.fintech_shakhvorostov.databinding.FragmentPopularListBinding
import deuscringe.shvv.fintech_shakhvorostov.ui.adapters.RecyclerAdapter
import deuscringe.shvv.fintech_shakhvorostov.ui.viewmodel.PopularListViewModel
import kotlinx.coroutines.launch

class PopularListFragment: Fragment() {
    private val viewModel: PopularListViewModel by viewModels<PopularListViewModel>()

    private var _binding: FragmentPopularListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val recyclerAdapter = RecyclerAdapter(navController,
            onItemLongClick = {position ->
                viewModel.addToFavorite(position)
                Log.i("LongClick", "$position")
            }
        )

        binding.rvHolder.adapter = recyclerAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isErrorVisible.collect {
                    binding.tvError.isVisible = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            if (viewModel.filmsFlow.value == null) viewModel.getFilms()
        }

        binding.slHolder.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.slHolder.isRefreshing = false
                viewModel.getFilms()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isProgressBarVisible.collect {
                    binding.pbLoading.isVisible = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isRecyclerVisible.collect {
                    binding.rvHolder.isVisible = it
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filmsFlow.collect {
                    recyclerAdapter.submitList(it)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}