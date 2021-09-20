package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var createdImageView: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
//        val imageView  = findViewById<ImageView>(R.id.memeImageView)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }


    //https://meme-api.herokuapp.com/gimme/wholesomememes
    private fun loadMeme(){
        // Instantiate the RequestQueue.
        progressbar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme/wholesomememes"

// Request a string response from the provided URL.
        val jsonObjectRequest= JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
                createdImageView = response.getString("url")
                Glide.with(this,).load(createdImageView).listener(object: RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                      progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }
                }).into(imageView)
            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun nextMeme(view: android.view.View) {
    loadMeme()
    }
    fun shareMeme(view: android.view.View) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey checkout this meme $createdImageView")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Sharing the meme ...")
        startActivity(shareIntent)


    }
}