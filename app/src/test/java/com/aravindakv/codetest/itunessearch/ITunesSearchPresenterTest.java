package com.aravindakv.codetest.itunessearch;

import com.aravindakv.codetest.itunessearch.presenter.ITunesSearchPresenter;
import com.aravindakv.codetest.itunessearch.presenter.ShowResultCallback;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URLConnection;

import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import static junit.framework.Assert.assertEquals;

/**
 * Created by aravindakv on 13/02/18.
 */
@RunWith(JMockit.class)
public class ITunesSearchPresenterTest {

    ITunesSearchPresenter iTunesSearchPresenter;

    @Mocked
    private URLConnection connection;

    @Mocked
    private ITunesSearchPresenter.DownloaderTask downloaderTask;

    @Mocked
    private ShowResultCallback showResultCallback;

    @Test
    public void testItGeneratesCorrectURL() {
        String[] matchStrings = new String[] {"Michel", "Jackson"};
        iTunesSearchPresenter = new ITunesSearchPresenter(showResultCallback);
        iTunesSearchPresenter.setMatchTerms(matchStrings);

        String expectedString = "https://itunes.apple.com/search?term=Michel+Jackson";
        assertEquals(expectedString, iTunesSearchPresenter.getURLForConnection());
    }

    @Test
    public void itDownloadsiTunesJson() throws IOException {
        String[] matchStrings = new String[] {"Michel", "Jackson"};
        iTunesSearchPresenter = new ITunesSearchPresenter(showResultCallback);
        iTunesSearchPresenter.setMatchTerms(matchStrings);

        iTunesSearchPresenter.startDownload();

        new Verifications() {{
            showResultCallback.onDownloadCompleted();
        }};
    }

}