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
import androidx.navigation.fragment.navArgs
import coil3.load
import deuscringe.shvv.fintech_shakhvorostov.databinding.FragmentPopularListDetailedBinding
import deuscringe.shvv.fintech_shakhvorostov.ui.viewmodel.PopularListDetailedViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class PopularListDetailedFragment:Fragment() {

    private var _binding: FragmentPopularListDetailedBinding? = null
    private val binding get() = _binding!!

    private val args: PopularListDetailedFragmentArgs by navArgs()
    private val viewModel: PopularListDetailedViewModel by viewModels<PopularListDetailedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularListDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setContentVisibility(false)
        binding.pbLoading.isVisible = true
        binding.tvError.isVisible = false

        binding.slHolder.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.slHolder.isRefreshing = false
                Log.i("film id", "${args.filmId}")
                viewModel.getContent(args.filmId)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            if (viewModel.contentFlow.value == null) viewModel.getContent(args.filmId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.contentFlow.collect {it ->
                    if (it != null){
                        with(binding) {
                            tvName.text = it.nameRu
                            tvDesc.text = it.description
                            tvCountry.text = it.countries
                            tvGenres.text = it.genres
                            startShimmer()
                            shimmerImageView.startShimmer()
                            ivPoster.load(it.posterUrl){
                                listener(
                                    onSuccess = {imageRequest, successResult ->
                                        Log.i("IV", "suc")
                                        stopShimmer()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isProgressBarVisible.collect {
                    if (it != null) binding.pbLoading.isVisible = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isErrorVisible.collect {
                    if (it != null) binding.tvError.isVisible = it
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isContentVisible.collect {
                    if (it != null) setContentVisibility(it)
                }
            }
        }

    }

    private fun stopShimmer() {
        with (binding){
            shimmerImageView.visibility = View.GONE
            ivPoster.visibility = View.VISIBLE
            shimmerImageView.stopShimmer()
        }
    }

    private fun startShimmer() {
        with (binding){
            shimmerImageView.startShimmer()
            shimmerImageView.visibility = View.VISIBLE
            ivPoster.visibility = View.GONE
        }

    }

    private fun setContentVisibility(isVisible: Boolean){
        with(binding) {
            ivShimmerContainer.isVisible = isVisible
            ivPoster.isVisible = isVisible
            tvName.isVisible = isVisible
            tvDesc.isVisible = isVisible
            tvGenreStatic.isVisible = isVisible
            tvGenres.isVisible = isVisible
            tvCountryStatic.isVisible = isVisible
            tvCountry.isVisible = isVisible
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}