package deuscringe.shvv.fintech_shakhvorostov

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

object HTTPReq {
    fun getRequest(
        type: String,
        page: String,
        callback: VolleyCallback,
    ): StringRequest {
        val url = "https://kinopoiskapiunofficial.tech/api/v2.2/films/$type$page"

        return object : StringRequest(Method.GET,
            url,
            Response.Listener { response: String -> callback.onSuccess(response) },
            Response.ErrorListener { error: VolleyError -> callback.onError(error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["X-API-KEY"] = API_KEY
                params["Content-Type"] = "application/json"
                return params
            }
        }
    }
}