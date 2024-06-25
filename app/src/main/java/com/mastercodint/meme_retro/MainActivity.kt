package com.mastercodint.meme_retro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide

import com.mastercodint.meme_retro.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class MainActivity : AppCompatActivity() {
   lateinit var binding: ActivityMainBinding
   val BASE_URL="https://meme-api.com/"
   lateinit var memeList: MutableList<Meme>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        getData()
        binding.nextButton.setOnClickListener {
            getData()
        }

    }

    private fun getData() {
        val RetroFitBuilder=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(ApiInterface::class.java)

        val retroFitData=RetroFitBuilder.getData()
        retroFitData.enqueue(object : Callback<Meme?> {
            override fun onResponse(call: Call<Meme?>, response: Response<Meme?>) {

                val data=response.body()!!
                binding.textView.text=data.title
                Glide.with(this@MainActivity).load(data.url).into(binding.imageView)

            }

            override fun onFailure(call: Call<Meme?>, t: Throwable) {
                Log.d("MainActivity", "API call failed: ${t.message}")
                Toast.makeText(this@MainActivity, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

}