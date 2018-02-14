package com.aravindakv.codetest.itunessearch.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aravindakv.codetest.itunessearch.model.ITunesResult;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import codetest.aravindakv.com.itunessearch.R;

/**
 * Created by aravindakv on 14/02/18.
 */

public class DetailActivity extends AppCompatActivity {

    TextView trackName, artistName, albumName, price, releaseDate;
    ImageView previewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Intent intent = getIntent();

        trackName = findViewById(R.id.trackName);
        trackName.setText("Track: " + intent.getStringExtra("trackName"));

        artistName = findViewById(R.id.artistName);
        artistName.setText("Artist: " + intent.getStringExtra("artistName"));

        albumName = findViewById(R.id.albumName);
        albumName.setText("Album: " + intent.getStringExtra("albumName"));

        price = findViewById(R.id.price);
        price.setText("Price: " + intent.getStringExtra("price"));

        releaseDate = findViewById(R.id.releaseDate);
        releaseDate.setText("Price: " + intent.getStringExtra("releaseDate"));

        previewImage = findViewById(R.id.previewImage);

        //TODO: Download images

        new DownloadImagesTask(intent.getStringExtra("imageUrl")).execute();
    }

    public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {

        private final String url;

        private String TAG = "DownloadImagesTask";

        public DownloadImagesTask(String imageurl) {
            this.url = imageurl;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            return download_Image(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            previewImage.setImageBitmap(result);
        }

        private Bitmap download_Image(String url) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
        }
    }

}
