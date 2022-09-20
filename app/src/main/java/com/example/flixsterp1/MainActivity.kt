package com.example.flixsterp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var client: OkHttpClient = OkHttpClient();
    var movies: ArrayList<Movie> = arrayListOf<Movie>()
    // creates the adapter
    val adapter: RecyclerAdapter = RecyclerAdapter(movies)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // creates recyclerview reference
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        run()
        adapter.notifyDataSetChanged()
    }
    // Background thread which calls API
    fun run() {
        // builds request to API with api key
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1")
            .build()
        // calls request
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            // function triggered on response from API
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    // creates GSON class for deserializing into Movie class
                    val gson = Gson()
                    // array of Movies
                    val arrayTutorialType = object : TypeToken<Array<Movie>>() {}.type
                    // convert response into Json object
                    val obj = JSONObject(response.body!!.string())
                    println(obj)
                    // deserialize from Json using GSON into an array of movies
                    var tutorials: Array<Movie> = gson.fromJson(obj.getJSONArray("results").toString(), arrayTutorialType)
                    //add all movies from the api call
                    movies.addAll(tutorials)
                    // Calls the user interface thread and notifys the adapter that we added new movies
                    runOnUiThread{
                        adapter.notifyDataSetChanged()
                    }
                    for (movie in tutorials) {
                        println(movie.backdrop_path + " " + movie.original_title + " " + movie.overview)
                    }
                }
            }
        })
    }
}