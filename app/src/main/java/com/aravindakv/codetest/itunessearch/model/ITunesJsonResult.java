package com.aravindakv.codetest.itunessearch.model;

import java.util.List;

/**
 * Created by aravindakv on 13/02/18.
 */

public class ITunesJsonResult {

    private int resultCount;

    private List<ITunesResult> results = null;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<ITunesResult> getResults() {
        return results;
    }

    public void setResults(List<ITunesResult> results) {
        this.results = results;
    }

}
