package deuscringe.shvv.fintech_shakhvorostov.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import deuscringe.shvv.fintech_shakhvorostov.databinding.ItemFilmCardBinding
import deuscringe.shvv.fintech_shakhvorostov.ui.model.FilmModel
import deuscringe.shvv.fintech_shakhvorostov.ui.view.PopularListFragmentDirections

class RecyclerAdapter(
    val navController: NavController,
    val onItemLongClick: (itemPosition: Int) -> Unit,

): ListAdapter<FilmModel, RecyclerAdapter.RecyclerViewHolder>(
    TestDiffCallback()
)
    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            return RecyclerViewHolder(
                ItemFilmCardBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(
            holder: RecyclerViewHolder,
            position: Int
        ) {
            holder.bind(getItem(position), position)

        }

        inner class RecyclerViewHolder(
            private val binding: ItemFilmCardBinding
        ): RecyclerView.ViewHolder(binding.root){

             fun bind (films: FilmModel, position: Int){
                 with(binding){
                     tvFilmName.text = films.name
                     tvShortDesc.text = films.shortDesc
                     icStar.isVisible = films.isFavorite
                     startShimmer()
                     ivPreview.load(films.posterUrlPreview){
                        listener(
                            onSuccess = {imageRequest,successResult ->
                                stopShimmer()
                            }
                        )
                     }

                     container.setOnClickListener {
                        navigate(films.filmId)
                     }
                     container.setOnLongClickListener {
                         onItemLongClick(position)
                         Log.i("adapterLongClick", "$position")
                         true
                     }
                 }
            }

            private fun navigate(filmId: Int) {
                val action = PopularListFragmentDirections.actionToPopularListDetailedFragment(
                    filmId
                )
                navController.navigate(action)
            }

            fun startShimmer() {
                binding.shimmerImageView.apply{
                    startShimmer()
                    visibility = View.VISIBLE
                }

            }

            @SuppressLint("ResourceAsColor")
            fun stopShimmer() {
                binding.shimmerImageView.apply {
                    visibility = View.GONE
                    stopShimmer()
                }
            }
        }





}
class TestDiffCallback : DiffUtil.ItemCallback<FilmModel>() {
    override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
        return oldItem == newItem
    }
}
