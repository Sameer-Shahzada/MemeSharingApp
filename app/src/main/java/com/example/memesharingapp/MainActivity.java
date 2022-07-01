package com.example.memesharingapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    // define loadMeme method data field here to access everywhere in class
    String responseResult = null;  // store default value for string variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // invoke loadMeme method
        loadMeme();
    }

    private void loadMeme(){
        // set progressBar
        ProgressBar progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.VISIBLE);

        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);    -> no more need this line because we make a singleton instance to call volley
        String url = "https://meme-api.herokuapp.com/gimme";    // store given API in url variable

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                            String responseResult = null;  // store default value for string variable
                        try {
                           responseResult = response.getString("url");
                            System.out.println(responseResult + "Hello Sameer"); // this statement for check API response
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ImageView img = findViewById(R.id.memeImageView);   // way of access imageView
                        Glide.with(MainActivity.this).load(responseResult).listener(new RequestListener<Drawable>() {
                            // set listener methods
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressbar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressbar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(img);  // Glide library
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("error - ","something went wrong.");
                    }
                });
          // Add the request to the RequestQueue
//        queue.add(jsonObjectRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void shareMeme(View view) {

        // 1-> Way of send the plain text with url

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout this cool meme I got this from Reddit" + " " + responseResult);
        Intent chooser = Intent.createChooser(intent,"Share this meme using ...");
        startActivity(chooser);

        // 2-> Way of send the selected image

//        Uri pictureUri = Uri.parse(responseResult);     //-> need to know what is uri and what is the work of parse everything about uri and parse?
//        Intent intent = new Intent((Intent.ACTION_SEND));
//        intent.setType("image/jpeg,png,jpg,gif");   // -> how to set
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(responseResult));
//        Intent chooser = Intent.createChooser(intent, "Share this meme image using ...");
//        startActivity(chooser);


//        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(downloadedPic));


        // ------------------Wastage code------------------------------------
        //        File downloadedPic =  new File(
//                Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_DOWNLOADS),
//                "q.jpeg");
//

    }

    public void nextMeme(View view) {
        loadMeme();
    }
}