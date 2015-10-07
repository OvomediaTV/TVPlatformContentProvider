package com.example.ovo.content.provider.test;

import java.util.List;

import tv.ovomedia.launcher.util.PlaylistProviderUtils;
import tv.ovomedia.launcher.util.PlaylistProviderUtils.PlaylistProviderInfo;
import tv.ovomedia.launcher.view.PlaylistItem;
import tv.ovomedia.tool.Log;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class TestMainActivity extends Activity {
    private final static String TAG = TestMainActivity.class.getSimpleName();
    private Button mGetPublishedListButton = null;
    private List<PlaylistProviderInfo> mProviderList = null;
    private String mAuthority = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fetchProvider();
        initUi();
    }
    
    private void fetchProvider() {
        mProviderList = PlaylistProviderUtils.getPlaylistProviders(this);
        for (int i = 0; i < mProviderList.size(); i++) {
            PlaylistProviderInfo info = mProviderList.get(i);
            mAuthority = info.getPackageName() + PlaylistProviderUtils.AUTHORITY_SUFFIX;
            Log.d(TAG, "fetchProvider(): Index=" + i 
                    + ", Package=" + info.getPackageName()
                    + ", Name=" + info.getAppName()
                    + ", Authority=" + mAuthority);
        }
    }
    
    private void initUi() {
        mGetPublishedListButton = (Button) findViewById(R.id.button1);
        mGetPublishedListButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PlaylistItem> items = 
                        PlaylistProviderUtils.getPublishedItems(
                                getApplicationContext(), mAuthority);
                for (int i = 0; i < items.size(); i++) {
                    PlaylistItem item = items.get(i);
                    Log.d(TAG, "mGetPublishedListButton.onClick(): " + item.getTitle()
                            + ", videoId=" + item.getVideoId());
                }
                
                if (items.size() > 0) {
                    Uri uri = Uri.parse("onlineNews://watch/" + items.get(0).getVideoId());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
    }
}
