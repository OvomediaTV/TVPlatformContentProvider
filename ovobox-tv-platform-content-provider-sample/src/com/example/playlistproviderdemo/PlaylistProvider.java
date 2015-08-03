package com.example.playlistproviderdemo;

import tv.ovomedia.playlistprovider.AbsPlaylistProvider;
import tv.ovomedia.playlistprovider.LoginState;
import tv.ovomedia.playlistprovider.ProviderPlaylistItem;
import android.content.Context;
import android.content.SharedPreferences;

public class PlaylistProvider extends AbsPlaylistProvider{

    @Override
    protected ProviderPlaylistItem getLastPlayedItem() {
        String lastVideoId = loadLastPlayedVideoId();
        
        if (lastVideoId != null) {
           /*
            * Give the last played video back to OVO launcher. 
            */
            return new ProviderPlaylistItem(
                    lastVideoId,
                    "測試直播",
                    "https://www.youtube.com/watch?v=ESKjSwcswBM",
                    ProviderPlaylistItem.STREAM_TYPE_LIVE);
        } else {
            /*
             * If there was no saved video id or the saved video id 
             * was already not in current playlist, then give the 1st 
             * item in the playlist to OVO launcher.
             */
            return new ProviderPlaylistItem(
                    "123",
                    "測試直播",
                    "https://www.youtube.com/watch?v=ESKjSwcswBM",
                    ProviderPlaylistItem.STREAM_TYPE_LIVE);    
        }
    }

    @Override
    protected ProviderPlaylistItem getNextItemInPlaylist(String videoId) {
        // What is the next video of videoId?
        return new ProviderPlaylistItem(
                "123",
                "測試直播",
                "https://www.youtube.com/watch?v=ESKjSwcswBM",
                ProviderPlaylistItem.STREAM_TYPE_LIVE);
    }

    @Override
    protected ProviderPlaylistItem getPrevItemInPlaylist(String videoId) {
        // What is the previous video of videoId?
        return new ProviderPlaylistItem(
                "123",
                "測試直播",
                "https://www.youtube.com/watch?v=ESKjSwcswBM",
                ProviderPlaylistItem.STREAM_TYPE_LIVE);
    }

    @Override
    protected void setLastPlayedItem(String videoId) {
        /*
         * Save it somewhere such as a SharedPreference, 
         * it will be useful in 'getLastPlayedItem()'.
         */
        saveLastPlayedVideoId(videoId);
    }

    @Override
    protected void reportWatching(String videoId) {
        // Implement this if you want to record user behavior.
    }

    @Override
    protected void reportWatched(String videoId) {
        // Implement this if you want to record user behavior.        
    }

    @Override
    protected void reportContinueWatching(String videoId) {
        // Implement this if you want to record user behavior.
    }

    @Override
    protected void reportSkipped(String videoId) {
        // Implement this if you want to record user behavior.
    }

    @Override
    protected LoginState getLoginState() {
        // We don't need our user to login, so we return true here.
        return new LoginState(true);
    }

    private void saveLastPlayedVideoId(String videoId) {
        SharedPreferences pref = getContext().getApplicationContext()
                .getSharedPreferences(
                        "pref_settings",
                        Context.MODE_PRIVATE);
        pref.edit().putString("lastVideoId", videoId).commit();
    }
    
    private String loadLastPlayedVideoId() {
        SharedPreferences pref = getContext().getApplicationContext()
                .getSharedPreferences(
                        "pref_settings",
                        Context.MODE_PRIVATE);
        return pref.getString("lastVideoId", null);
    }
}
