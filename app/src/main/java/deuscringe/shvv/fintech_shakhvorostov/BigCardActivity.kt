package deuscringe.shvv.fintech_shakhvorostov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import org.json.JSONObject

class BigCardActivity : AppCompatActivity() {

    private lateinit var imgCard: ImageView
    private lateinit var nameTV: TextView
    private lateinit var descTV: TextView
    private lateinit var genreTV: TextView
    private lateinit var countryTV: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.big_card)

        imgCard = findViewById(R.id.imgCard)
        nameTV = findViewById(R.id.nameTV)
        descTV = findViewById(R.id.descTV)
        genreTV = findViewById(R.id.genreTV)
        countryTV = findViewById(R.id.countryTV)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {

            val receivedData = intent.getStringExtra("data_key")
            parseData(receivedData)

            swipeRefreshLayout.isRefreshing = false
        }

        val receivedData = intent.getStringExtra("data_key")
        parseData(receivedData)
    }

    private fun parseData(receivedData: String?) {
        val obj = JSONObject(receivedData)
        val cover = obj.getString("coverUrl")
        val name = obj.getString("nameRu")
        val desc = obj.getString("description")
        val genreList = ArrayList<String> ()
        for (i in 0 until obj.getJSONArray("genres").length()){
            genreList.add(obj.getJSONArray("genres").getJSONObject(i).getString("genre"))
        }
        val countryList = ArrayList<String> ()
        for (i in 0 until obj.getJSONArray("countries").length()){
            countryList.add(obj.getJSONArray("countries").getJSONObject(i).getString("country"))
        }

        Log.d("MyLog", "coverUrl is: $cover")
        Log.d("MyLog", "description is: $desc")
        Log.d("MyLog", "genre is: $genreList")
        Log.d("MyLog", "countries is: $countryList")

        setChanges(cover,name,desc, genreList,countryList)
    }

    private fun setChanges(
        cover: String,
        name: String,
        desc: String,
        genreList: ArrayList<String>,
        countryList: ArrayList<String>
    ){
        var genreText: String = ""
        var countryText:String =""
        Picasso.get().load(cover).into(imgCard)
        nameTV.text = name
        descTV.text = desc
        for (i in 0 until genreList.size)
            genreText += (genreList.get(i) + " ")
        for (i in 0 until countryList.size)
            countryText += (countryList.get(i) + " ")
        countryTV.text = countryText
        genreTV.text = genreText



    }

}