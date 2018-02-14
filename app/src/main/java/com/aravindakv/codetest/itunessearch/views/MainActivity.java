package com.aravindakv.codetest.itunessearch.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.aravindakv.codetest.itunessearch.model.ITunesResult;
import com.aravindakv.codetest.itunessearch.presenter.ITunesSearchPresenter;
import com.aravindakv.codetest.itunessearch.presenter.ShowResultCallback;

import java.util.ArrayList;
import java.util.List;

import codetest.aravindakv.com.itunessearch.R;

public class MainActivity extends AppCompatActivity implements ShowResultCallback {

    ITunesSearchPresenter mPresenter;

    private EditText searchEditText;
    private Button searchButton;
    private ProgressBar mProgressBar;
    private RecyclerView recyclerView;
    private ITunesListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializePresenter();
        initializeViews();
        initRecycleView();
    }

    private void initializePresenter() {
        mPresenter = new ITunesSearchPresenter(this);
    }

    private void initializeViews() {
        searchEditText = findViewById(R.id.search_editor);
        mProgressBar = findViewById(R.id.progressBar_cyclic);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                String inputText = searchEditText.getText().toString();
                if (!inputText.isEmpty()) {
                    mPresenter.setMatchTerms(inputText.split(" "));
                    mPresenter.startDownload();
                }
            }
        });
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void onDownloadCompleted() {
        mProgressBar.setVisibility(View.GONE);
        hideKeyboard();

        final List<ITunesResult> searchResults = mPresenter.getListOfTunes();
        List<MainListItem> recycleViewListItems = new ArrayList<>();
        for(ITunesResult result:searchResults) {
            MainListItem item = new MainListItem();
            item.setArtistName(result.getArtistName());
            item.setTrackName(result.getTrackName());
            item.setImageUrl(result.getArtworkUrl100());
            recycleViewListItems.add(item);
        }
        mAdapter = new ITunesListAdapter(getApplicationContext(), recycleViewListItems);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                ITunesResult result = searchResults.get(itemPosition);
                intent.putExtra("trackName", result.getTrackName());
                intent.putExtra("artistName", result.getArtistName());
                intent.putExtra("albumName", result.getCollectionName());
                intent.putExtra("price", String.valueOf(result.getTrackPrice()));
                intent.putExtra("releaseDate", result.getReleaseDate());
                intent.putExtra("imageUrl", result.getArtworkUrl100());
                startActivity(intent);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }
}
