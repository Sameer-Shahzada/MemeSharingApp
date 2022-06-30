package com.example.memesharingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // invoke loadMeme method
        loadMeme();
    }

    private void loadMeme(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme";    // store given API in url variable

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                            String responseResult = null;  // store default value for string variable
                        try {
                           responseResult = response.getString("url");
                            System.out.println(responseResult + "Hello Sameer"); // this statement for check API response
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ImageView img = findViewById(R.id.memeImageView);   // way of access imageView
                        Glide.with(MainActivity.this).load(responseResult).into(img);  // Glide library
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("error - ","something went wrong.");
                    }
                });
        queue.add(jsonObjectRequest);
    }
    public void shareMeme(View view) {

    }

    public void nextMeme(View view) {

    }
}