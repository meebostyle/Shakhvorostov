package deuscringe.shvv.fintech_shakhvorostov.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import deuscringe.shvv.fintech_shakhvorostov.BigCardActivity
import deuscringe.shvv.fintech_shakhvorostov.HTTPReq
import deuscringe.shvv.fintech_shakhvorostov.R
import deuscringe.shvv.fintech_shakhvorostov.VolleyCallback
import deuscringe.shvv.fintech_shakhvorostov.databinding.FilmItemBinding


class FilmAdapter : ListAdapter <FilmModel, FilmAdapter.Holder> (Holder.Comporator()) {

    class Holder(view: View):RecyclerView.ViewHolder(view){
        private val binding = FilmItemBinding.bind(view)
        fun bind (film: FilmModel)  = with (binding) {
            Picasso.get().load(film.imageId).into(cvFilmImg)
            tvFilmName.text = film.name
            tvShortDesc.text = film.genre + film.year


            container.setOnLongClickListener { view ->
                handleLongClick(view.context, film)
                true
            }
            container.setOnClickListener(View.OnClickListener {
                getResourses(it.context, film.id)



            })


        }



        private fun handleLongClick(context: Context, film: FilmModel) {

            Toast.makeText(context, "Так можно будет добавить фильм в избранное", Toast.LENGTH_SHORT).show()


        }




        fun getResourses(context: Context, id:Int) {
            val queue = Volley.newRequestQueue(context)
            queue.add(
                HTTPReq.getRequest(
                    "/",
                    id.toString(),
                    object : VolleyCallback {

                        override fun onSuccess(result: String) {
                            val decodedResult = String(result.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
                            Log.d("Mylog","My result is: $decodedResult")
                            goToNewActivity(context, BigCardActivity::class.java, decodedResult)
                         }








                        override fun onError(result: String?) {
                            Log.e("MyLog", "Volley error, ID is: $id")
                            Toast.makeText(context, "Что-то пошло не так... проверьте подключение к интернету", Toast.LENGTH_SHORT).show()

                        }

                    })
            )

        }
        private fun goToNewActivity(context: Context, activityClass: Class<BigCardActivity>, data: String) {
        val intent = Intent(context, activityClass)
        intent.putExtra("data_key", data)
        context.startActivity(intent)
    }

        class Comporator : DiffUtil.ItemCallback<FilmModel>(){
            override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
                return oldItem == newItem
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

}