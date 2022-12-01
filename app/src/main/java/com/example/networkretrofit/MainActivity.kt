package com.example.networkretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.networkretrofit.Constants.APP_TAG
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.network.RetrofitEx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofitEx: RetrofitEx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            binding.mainActivity = this

            retrofitEx = RetrofitEx()

        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun testButton() {
        CoroutineScope(Dispatchers.IO).launch {
            //retrofitEx.getUsersCallDataClassEnqueue()

            retrofitEx.getUsersCallAnyExecute().let {
                Log.d(APP_TAG, "getUsersCallAny(): $it")
            }


        }
    }
}

