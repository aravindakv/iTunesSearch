package com.aravindakv.codetest.itunessearch.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.aravindakv.codetest.itunessearch.model.ITunesResult;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.aravindakv.codetest.itunessearch.model.ITunesJsonResult;

/**
 * Created by aravindakv on 13/02/18.
 */

public class ITunesSearchPresenter {
    private static String BASE_URL = "https://itunes.apple.com/search?term=";

    private final ShowResultCallback showResultCallback;

    private String[] matchTerms;

    private ITunesJsonResult iTunesJsonResult;
    private static String jsonString;

    public ITunesSearchPresenter(ShowResultCallback callback) {
        this.showResultCallback = callback;
    }

    public void setMatchTerms(String[] terms) {
        this.matchTerms = terms;
    }

    public String getURLForConnection() {
        String conCatTerms = BASE_URL;
        for (String term : matchTerms) {
            conCatTerms += term + "+";
        }
        return conCatTerms.substring(0, conCatTerms.length() - 1);
    }

    public void startDownload() {
        new DownloaderTask(getURLForConnection()).execute();
    }

    public int getResultCount() {
        if (iTunesJsonResult != null) {
            return iTunesJsonResult.getResultCount();
        }
        return 0;
    }

    public List<ITunesResult> getListOfTunes() {
        if (iTunesJsonResult != null) {
            return iTunesJsonResult.getResults();
        }
        return null;
    }

    public class DownloaderTask extends AsyncTask<String, String, String> {
        private String urlForConnection = null;

        public DownloaderTask(String urlForConnection) {
            this.urlForConnection = urlForConnection;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(urlForConnection);
                URLConnection connection = url.openConnection();
                connection.connect();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder str = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
                reader.close();
                jsonString = str.toString();
            } catch (Exception e) {
                Log.d("DownloaderTask",
                        "Unable to download Json: " + e.getMessage());
            }
            return jsonString;
        }

        @Override
        protected void onPostExecute(String result) {
            if (jsonString != null) {
                iTunesJsonResult = new Gson().fromJson(jsonString, ITunesJsonResult.class);
                showResultCallback.onDownloadCompleted();
                Log.i("Aravinda", "items: " + getResultCount());
            }
        }
    }

}
