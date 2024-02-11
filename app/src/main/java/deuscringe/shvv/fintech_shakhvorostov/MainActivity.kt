package deuscringe.shvv.fintech_shakhvorostov


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import deuscringe.shvv.fintech_shakhvorostov.databinding.ActivityMainBinding
import deuscringe.shvv.fintech_shakhvorostov.fragments.BlankFragment


const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"


class MainActivity : AppCompatActivity(), BlankFragment.FragmentCallbackError,BlankFragment.FragmentCallbackSuccess {

        private lateinit var blankFragment: BlankFragment
        private lateinit var errorMessage: ConstraintLayout
        private lateinit var binding: ActivityMainBinding
        private lateinit var progressBar: ProgressBar
        override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)








        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
                )
            super.onCreate(savedInstanceState)
            setContentView(binding.root)
            blankFragment = BlankFragment.newInstance(this, this)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainPlace, blankFragment)
                .commit()
                errorMessage = findViewById(R.id.constrError)



                progressBar = findViewById<ProgressBar>(R.id.progressBar)
                val errorBtn = findViewById<AppCompatButton>(R.id.btnError)
                showProgressBar(progressBar, errorMessage, blankFragment.checkAct())
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ hideProgressBar(progressBar,errorMessage,blankFragment.checkAct())
                }, 3000)
                errorBtn.setOnClickListener(View.OnClickListener {
                    showProgressBar(progressBar,errorMessage,blankFragment.checkAct())
                    blankFragment.getResourses(this)
                    Log.i("click", "click")
                    handler.postDelayed({ hideProgressBar(progressBar,errorMessage,blankFragment.checkAct())
                    }, 3000)

                    })







    }

    override fun onErrorMessageClicked() {
        Log.v("ping", "ping errr")
    }
    override fun onSuccessMessageClicked() {
        errorMessage.visibility = View.GONE
        progressBar.visibility = View.GONE


    }

    private fun showProgressBar(progressBar: ProgressBar,errorMessage: ConstraintLayout, check: Boolean?) {
        try {
            errorMessage.visibility = View.GONE
            if (!check!!)
                progressBar.visibility = View.VISIBLE
        }
        catch (e: Exception){

        }

    }

    private fun hideProgressBar(progressBar: ProgressBar, errorMessage: ConstraintLayout, check: Boolean?) {
        try {
            progressBar.visibility = View.GONE
            if (!check!!)
                errorMessage.visibility = View.VISIBLE
        }
        catch (e: Exception){

        }

    }

}


