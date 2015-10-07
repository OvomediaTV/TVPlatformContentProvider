package tv.ovomedia.launcher.util;

import java.util.ArrayList;
import java.util.List;

import tv.ovomedia.api.VideoBlockWhiteList;
import tv.ovomedia.launcher.view.PlaylistItem;
import tv.ovomedia.tool.Log;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;

public class PlaylistProviderUtils {
    private static final String TAG = 
            PlaylistProviderUtils.class.getSimpleName();
    
    public static final String AUTHORITY_SUFFIX = ".provider.OVOPlaylistProvider";
    public static final String AUTHORITY_SUFFIX_OLD = ".provider.playlistProvider";
    
    // Queries without videoId required.
    private static final String PATH_LAST_PLAYED_ITEM = 
            "playlistItem/getLast"; 
    
    // Queries with videoId required.
    private static final String PATH_SET_LAST_PLAYED_ITEM = 
            "playlistItem/setLast";
    private static final String PATH_NEXT_ITEM = 
            "playlistItem/getNext";
    private static final String PATH_PREV_ITEM = 
            "playlistItem/getPrev";
    private static final String PATH_REPORT_WATCHING = 
            "playlistItem/reportWatching";
    private static final String PATH_REPORT_WATCHED = 
            "playlistItem/reportWatched";
    private static final String PATH_REPORT_CONTINUE_WATCHING = 
            "playlistItem/reportContinue";
    private static final String PATH_REPORT_SKIPPED = 
            "playlistItem/reportSkipped";
    private static final String PATH_GET_STATE = 
            "state";
    private static final String PATH_GET_PUBLISHED =
            "playlistItem/getPublished";
    
    // Returned playlist item columns.
    public static final String COL_VIDEO_ID = "videoId";
    public static final String COL_VIDEO_TITLE = "videoTitle";
    public static final String COL_VIDEO_URL = "videoUrl";
    public static final String COL_STREAM_TYPE = "streamType";
    
    // Returned state columns.
    public static final String COL_IS_LOGGED_IN = "isLoggedIn";
    
    public static List<PlaylistItem> getPublishedItems(
            Context context, String authority) {
        Cursor c = null;
        ArrayList<PlaylistItem> publishedList = new ArrayList<PlaylistItem>();
        
        try {
            c = context.getContentResolver().query(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_GET_PUBLISHED,
                            null)),
                    null,
                    null,
                    null,
                    null);
            
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    String videoId = c.getString(c.getColumnIndex(COL_VIDEO_ID));
                    String title = c.getString(c.getColumnIndex(COL_VIDEO_TITLE));
                    String url = c.getString(c.getColumnIndex(COL_VIDEO_URL));
                    int streamType = PlaylistItem.STREAM_TYPE_FILE;
                    
                    try {
                        streamType = Integer.parseInt(
                                c.getString(c.getColumnIndex(COL_STREAM_TYPE)));
                    } catch (NumberFormatException e) {
                    }
                    
                    PlaylistItem videoItem = new PlaylistItem(
                            videoId,
                            title,
                            url,
                            streamType,
                            authority);
                    publishedList.add(videoItem);
                } while(c.moveToNext());
                
                return publishedList;
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        
        return null;
    }
    
    public static PlaylistItem getLastPlayedItem(Context context, 
            String authority) {
        Cursor c = null;
        
        try {
            c = context.getContentResolver().query(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_LAST_PLAYED_ITEM,
                            null)),
                    null,
                    null,
                    null,
                    null);
            
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                String videoId = c.getString(c.getColumnIndex(COL_VIDEO_ID));
                String title = c.getString(c.getColumnIndex(COL_VIDEO_TITLE));
                String url = c.getString(c.getColumnIndex(COL_VIDEO_URL));
                int streamType = PlaylistItem.STREAM_TYPE_FILE;
                
                try {
                    streamType = Integer.parseInt(
                            c.getString(c.getColumnIndex(COL_STREAM_TYPE)));
                } catch (NumberFormatException e) {
                }
                
                PlaylistItem videoItem = new PlaylistItem(
                        videoId,
                        title,
                        url,
                        streamType,
                        authority);
                return videoItem;
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        
        return null;
    }
    
    public static PlaylistItem getNextItem(Context context, 
            String authority, String currentVideoId) {
        Cursor c = null;
        
        try {
            c = context.getContentResolver().query(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_NEXT_ITEM,
                            currentVideoId)),
                    null,
                    null,
                    null,
                    null);
            
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                String videoId = c.getString(c.getColumnIndex(COL_VIDEO_ID));
                String title = c.getString(c.getColumnIndex(COL_VIDEO_TITLE));
                String url = c.getString(c.getColumnIndex(COL_VIDEO_URL));
                int streamType = PlaylistItem.STREAM_TYPE_FILE;
                
                try {
                    streamType = Integer.parseInt(
                            c.getString(c.getColumnIndex(COL_STREAM_TYPE)));
                } catch (NumberFormatException e) {
                }
                
                PlaylistItem videoItem = new PlaylistItem(
                        videoId,
                        title,
                        url,
                        streamType,
                        authority);
                return videoItem;
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        
        return null;
    }
    
    public static PlaylistItem getPrevItem(Context context, 
            String authority, String currentVideoId) {
        Cursor c = null;
        
        try {
            c = context.getContentResolver().query(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_PREV_ITEM,
                            currentVideoId)),
                    null,
                    null,
                    null,
                    null);
            
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                String videoId = c.getString(c.getColumnIndex(COL_VIDEO_ID));
                String title = c.getString(c.getColumnIndex(COL_VIDEO_TITLE));
                String url = c.getString(c.getColumnIndex(COL_VIDEO_URL));
                int streamType = PlaylistItem.STREAM_TYPE_FILE;
                
                try {
                    streamType = Integer.parseInt(
                            c.getString(c.getColumnIndex(COL_STREAM_TYPE)));
                } catch (NumberFormatException e) {
                }
                
                PlaylistItem videoItem = new PlaylistItem(
                        videoId,
                        title,
                        url,
                        streamType,
                        authority);
                return videoItem;
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        
        return null;
    }
    
    public static boolean setLastPlayedItem(Context context, 
            String authority, String currentVideoId) {
        try {
            int affectedRows = context.getContentResolver().update(
                    Uri.parse(getQueryString(
                            authority, 
                            PATH_SET_LAST_PLAYED_ITEM, 
                            currentVideoId)),
                    new ContentValues(),
                    null,
                    null);
            
            if (affectedRows > 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "set last played item: no provider found");
        }
        
        return false;
    }

    public static boolean reportItemWatching(Context context, String authority, 
            String currentVideoId) {
        try {
            int affectedRows = context.getContentResolver().update(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_REPORT_WATCHING,
                            currentVideoId)),
                    new ContentValues(),
                    null,
                    null);
            
            if (affectedRows > 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "report item watching: no provider found");
        }
        
        return false;
    }
    
    public static boolean reportItemWatched(Context context, String authority, 
            String currentVideoId) {
        try {
            int affectedRows = context.getContentResolver().update(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_REPORT_WATCHED,
                            currentVideoId)),
                    new ContentValues(),
                    null,
                    null);
            
            if (affectedRows > 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "report item watched: no provider found");
        }
        
        return false;
    }
    
    public static boolean reportItemContinueWatching(Context context, String authority, 
            String currentVideoId) {
        try {
            int affectedRows = context.getContentResolver().update(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_REPORT_CONTINUE_WATCHING,
                            currentVideoId)),
                    new ContentValues(),
                    null,
                    null);
            
            if (affectedRows > 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "report item continue: no provider found");
        }
        
        return false;
    }
    
    public static boolean reportItemSkipped(Context context, String authority, 
            String currentVideoId) {
        try {
            int affectedRows = context.getContentResolver().update(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_REPORT_SKIPPED,
                            currentVideoId)),
                    new ContentValues(),
                    null,
                    null);
            
            if (affectedRows > 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "report item skipped: no provider found");
        }
        
        return false;
    }
    
    public static boolean getLoginState(Context context, String authority) {
        Cursor c = null;
        
        try {
            c = context.getContentResolver().query(
                    Uri.parse(getQueryString(
                            authority,
                            PATH_GET_STATE,
                            "login")),
                    null,
                    null,
                    null,
                    null);
            
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                String str = c.getString(c.getColumnIndex(COL_IS_LOGGED_IN));
                return Boolean.parseBoolean(str);
            }
        } finally {
            
            if (c != null) {
                c.close();
                c = null;
            }
        }
        
        /*
         * If this API is missing, there is a very high possibility that 
         * this APP does not require user to login.
         */
        return true;
    }
    
    private static String getQueryString(String authority, String path, 
            String param) {
        StringBuilder sb = new StringBuilder()
                .append("content://")
                .append(authority)
                .append("/")
                .append(path);
        
        if (param != null) {
            sb.append("/").append(param);
        }
        
        return sb.toString();
    }
    
    public static boolean isProviderExisting(Context context, String authority) {
        
        if (context == null || authority == null) {
            return false;
        }
        
        boolean exist = false;
        ContentProviderClient providerClient = context
                .getContentResolver()
                .acquireContentProviderClient(authority);
        
        if (providerClient != null) {
            exist = true;
        } else {
            exist = false;
        }
        
        return exist;
    }
    
    public static List<PlaylistProviderInfo> getPlaylistProviders(Context context) {
        PackageManager pm = (PackageManager) context.getPackageManager();
        List<PackageInfo> pkgInfoList = pm.getInstalledPackages(
                PackageManager.GET_PROVIDERS | PackageManager.GET_SIGNATURES);
        ArrayList<PlaylistProviderInfo> playlistProviderList = 
                new ArrayList<PlaylistProviderInfo>();
        VideoBlockWhiteList whiteList = new VideoBlockWhiteList(context);
        
        for(PackageInfo pkgInfo : pkgInfoList) {
            
            if (pkgInfo.providers == null || pkgInfo.signatures == null) {
                continue;
            }
            
            Signature[] signatures = pkgInfo.signatures;
            String sigHex = Integer.toHexString(signatures[0].hashCode());
            boolean valid = whiteList.checkValidation(
                    context, 
                    pkgInfo.packageName, 
                    sigHex);
            
            if (!valid) {
                continue;
            }
            
            for (ProviderInfo providerInfo : pkgInfo.providers) {
                String authority = providerInfo.authority;
                
                if (authority == null) {
                    continue;
                }
                
                if (authority.endsWith(AUTHORITY_SUFFIX) || 
                        authority.endsWith(AUTHORITY_SUFFIX_OLD)) {
                    // It a valid OVO playlist content provider.
                    PlaylistProviderInfo ppi = new PlaylistProviderInfo(
                            pkgInfo.applicationInfo.loadLabel(pm).toString(),
                            pkgInfo.packageName);
                    playlistProviderList.add(ppi);
                    break; // This prevents a package name from being added twice.
                }
            }
        }
        
        return playlistProviderList;
    }
    
    public static void updateWhiteList(Context context) {
        // VideoBlockWhiteList.class download new white list in its constructor.
        new VideoBlockWhiteList(context);
    }
    
    public static class PlaylistProviderInfo {
        private String mAppName = null;
        private String mPackageName = null;
        
        public PlaylistProviderInfo(String appName, String packageName) {
            mAppName = appName;
            mPackageName = packageName;
        }
        
        public String  getAppName() {
            return mAppName;
        }
        
        public String getPackageName() {
            return mPackageName;
        }
    }
}
