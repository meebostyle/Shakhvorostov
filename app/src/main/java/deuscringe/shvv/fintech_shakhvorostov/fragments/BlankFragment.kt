package deuscringe.shvv.fintech_shakhvorostov.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.toolbox.Volley
import deuscringe.shvv.fintech_shakhvorostov.HTTPReq
import deuscringe.shvv.fintech_shakhvorostov.MainViewModel
import deuscringe.shvv.fintech_shakhvorostov.R
import deuscringe.shvv.fintech_shakhvorostov.VolleyCallback
import deuscringe.shvv.fintech_shakhvorostov.adapters.FilmAdapter
import deuscringe.shvv.fintech_shakhvorostov.adapters.FilmModel
import deuscringe.shvv.fintech_shakhvorostov.databinding.FragmentBlankBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject



class BlankFragment : Fragment() {

    interface FragmentCallbackError {
        fun onErrorMessageClicked()
    }
    private var isRequestInProgress = false

    interface FragmentCallbackSuccess {
        fun onSuccessMessageClicked()
    }

    private lateinit var fragmentCallbackError: FragmentCallbackError
    private lateinit var fragmentCallbackSuccess: FragmentCallbackSuccess

    private lateinit var binding: FragmentBlankBinding

    private lateinit var adapter: FilmAdapter

    private val model: MainViewModel by activityViewModels()
    val list = ArrayList<FilmModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataJSON.observe(viewLifecycleOwner) {
            adapter.submitList(getDataCard(it))
        }


        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            var timeout = false
            val timeoutHandler = Handler()
            timeoutHandler.postDelayed({
                timeout = true
                swipeRefreshLayout.isRefreshing = false
            }, 3000)

            if ((list.size != 250) && (!isRequestInProgress)) {
                list.clear()
                adapter.notifyDataSetChanged()
                getResourses(requireContext())
                initRcView()
            } else {
                swipeRefreshLayout.isRefreshing = false
            }

            model.liveDataJSON.observe(viewLifecycleOwner) {
                adapter.submitList(getDataCard(it))
                if (!timeout) {
                    swipeRefreshLayout.isRefreshing = false
                    timeoutHandler.removeCallbacksAndMessages(null)
                }
            }
        }


    }

    private fun initRcView() = with(binding) {
        rcVPopular.layoutManager = LinearLayoutManager(activity)
        adapter = FilmAdapter()
        rcVPopular.adapter = adapter
        getResourses(requireContext())
    }


    private fun getDataCard(fItem: FilmModel): List<FilmModel> {
        if (list.indexOf(fItem) == -1) {
            list.add(fItem)
        }
        Log.d("MyLog", "WEW ${list.size}")
        Log.d("MyLog", "LOL ${fItem.id}")
        return list
    }


    companion object {
        private const val KEY_FILM_LIST = "film_list"

        @JvmStatic
        fun newInstance(
            callbackError: FragmentCallbackError,
            callbackSuccess: FragmentCallbackSuccess
        ) = BlankFragment().apply {
            fragmentCallbackError = callbackError
            fragmentCallbackSuccess = callbackSuccess
        }

    }




    fun getResourses(context: Context) {
        if (!isRequestInProgress) {
            isRequestInProgress = true
            val queue = Volley.newRequestQueue(context)

            queue.add(
                HTTPReq.getRequest(
                    "top?TOP_100_POPULAR_FILMS",
                    "",
                     object : VolleyCallback {
                        override fun onSuccess(result: String) {
                            val decodedResult =
                                String(result.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
                            val obj = JSONObject(decodedResult)
                            Log.d("JSON", obj.toString())
                            CoroutineScope(Dispatchers.IO).launch {
                                for (i in 1..obj.getInt("pagesCount")) {
                                    delay(100)
                                    queue.add(
                                        HTTPReq.getRequest(
                                            "top?TOP_100_POPULAR_FILMS",
                                            "&page=${i.toString()}",
                                            object : VolleyCallback {
                                                override fun onSuccess(result: String) {
                                                    isRequestInProgress = true
                                                    val decodedResult =
                                                        String(
                                                            result.toByteArray(Charsets.ISO_8859_1),
                                                            Charsets.UTF_8
                                                        )
                                                    val obj = JSONObject(decodedResult)
                                                    for (j in 0 until obj.getJSONArray("films")
                                                        .length()) {
                                                        parseCards(
                                                            obj.getJSONArray("films")
                                                                .getJSONObject(j)
                                                        )
                                                        fragmentCallbackSuccess.onSuccessMessageClicked()
                                                        binding.rcHolder.visibility = View.VISIBLE
                                                    }
                                                }

                                                override fun onError(result: String?) {
                                                    Log.e("MyLog", "Volley error")
                                                    Toast.makeText(
                                                        context,
                                                        "Что-то пошло не так, нам очень жаль попробуйте обновить страницу",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    isRequestInProgress = false
                                                    fragmentCallbackError.onErrorMessageClicked()
                                                    binding.rcHolder.visibility = View.GONE
                                                }
                                            })
                                    )

                                }
                            }
                            isRequestInProgress = false
                        }

                        override fun onError(result: String?) {
                            Log.e("MyLog", "Volley error")
                            Toast.makeText(
                                context,
                                "Что-то пошло не так, нам очень жаль попробуйте обновить страницу",
                                Toast.LENGTH_SHORT

                            )
                            isRequestInProgress = false
                            fragmentCallbackError.onErrorMessageClicked()
                            binding.rcHolder.visibility = View.GONE
                        }
                    })
            )


        }
    }


    fun parseCards(obj: JSONObject) {

        val posterUrl = obj.getString("posterUrlPreview")
        val nameRu = obj.getString("nameRu")
        val rating = obj.getString("rating")
        val filmId = Integer.valueOf(obj.getString("filmId"))
        val year = "(${obj.getString("year")})"

        val genreText =  (obj.getJSONArray("genres").getJSONObject(0).getString("genre") + " ")

        val item = FilmModel(posterUrl, nameRu, rating, filmId,year,genreText)
        model.liveDataJSON.value = item
    }

    fun checkAct (): Boolean{

        try {
            return this.binding.rcHolder.visibility == View.VISIBLE
        }
        catch (e: Exception){

            return false
    }


    }

}

