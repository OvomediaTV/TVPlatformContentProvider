package tv.ovomedia.playlistprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public abstract class AbsPlaylistProvider extends ContentProvider {
    public static final String TAG = AbsPlaylistProvider.class.getSimpleName();
    
    public static final String AUTHORITY_SUFFIX = ".provider.OVOPlaylistProvider";
    
    // IDs for UriMatcher.
    public static final int ID_GET_PLAYLIST = 1;
    public static final int ID_GET_LAST_PLAYED_ITEM = 2;
    public static final int ID_SET_LAST_PLAYED_ITEM = 3;
    public static final int ID_GET_NEXT_ITEM_IN_PLAYLIST = 4;
    public static final int ID_GET_PREV_ITEM_IN_PLAYLIST = 5;
    public static final int ID_REPORT_ITEM_WATCHING = 6;
    public static final int ID_REPORT_ITEM_WATCHED = 7;
    public static final int ID_REPORT_ITEM_CONTINUE_WATCHING = 8;
    public static final int ID_REPORT_ITEM_SKIPPED = 9;
    
    // Columns in returned values.
    public static final String COL_VIDEO_ID = "videoId";
    public static final String COL_VIDEO_TITLE = "videoTitle";
    public static final String COL_VIDEO_URL = "videoUrl";
    public static final String COL_STREAM_TYPE = "streamType";
    
    private UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private String mAuthority = null;

    @Override
    public boolean onCreate() {
        mAuthority = getAuthority();
//      mUriMatcher.addURI(mAuthority, "playlist/get", ID_GET_PLAYLIST);
        mUriMatcher.addURI(mAuthority, "playlistItem/getLast", ID_GET_LAST_PLAYED_ITEM);
        mUriMatcher.addURI(mAuthority, "playlistItem/setLast/*", ID_SET_LAST_PLAYED_ITEM);
        mUriMatcher.addURI(mAuthority, "playlistItem/getNext/*", ID_GET_NEXT_ITEM_IN_PLAYLIST);
        mUriMatcher.addURI(mAuthority, "playlistItem/getPrev/*", ID_GET_PREV_ITEM_IN_PLAYLIST);
        mUriMatcher.addURI(mAuthority, "playlistItem/reportWatching/*", ID_REPORT_ITEM_WATCHING);
        mUriMatcher.addURI(mAuthority, "playlistItem/reportWatched/*", ID_REPORT_ITEM_WATCHED);
        mUriMatcher.addURI(mAuthority, "playlistItem/reportContinue/*", ID_REPORT_ITEM_CONTINUE_WATCHING);
        mUriMatcher.addURI(mAuthority, "playlistItem/reportSkipped/*", ID_REPORT_ITEM_SKIPPED);
        return true;
    }
    
    /**
     * A convenient way to get the default authority visible by OVO launcher.
     *  
     * In order to make your playlist content provider visible by OVO launcher, 
     * the authority must be <app_package_name>.provider.OVOPlaylistProvider.
     * If you know what you are doing, you can override this method for a 
     * different authority.
     * 
     * @return The default authority.
     */
    private String getAuthority() {
        return getContext().getPackageName() + AUTHORITY_SUFFIX;
    }
    
    @Override
    public String getType(Uri uri) {
        int id = mUriMatcher.match(uri);
        
        switch(id) {
//        case ID_GET_PLAYLIST:
//            return "vnd.android.cursor.dir/vnd." + mAuthority + ".playlist";
        case ID_GET_LAST_PLAYED_ITEM:
        case ID_GET_NEXT_ITEM_IN_PLAYLIST:
        case ID_GET_PREV_ITEM_IN_PLAYLIST:
            return "vnd.android.cursor.item/vnd." + mAuthority + ".playlistItem";
        }
        
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, 
            String[] selectionArgs, String sortOrder) {
        int id = mUriMatcher.match(uri);
        
        switch(id) {
//        case ID_GET_PLAYLIST:
//            return queryLastPlaylist();
        case ID_GET_LAST_PLAYED_ITEM:
            return queryLastPlayedItem();
        case ID_GET_NEXT_ITEM_IN_PLAYLIST:
            return queryNextItemInPlaylist(uri.getLastPathSegment());
        case ID_GET_PREV_ITEM_IN_PLAYLIST:
            return queryPrevItemInPlaylist(uri.getLastPathSegment());
        }
        
        return null;
    }

//    protected abstract List<PlaylistItem> getLastPlaylist();
//    
//    private Cursor queryLastPlaylist() {
//        List<PlaylistItem> playlist = getLastPlaylist();
//        MatrixCursor cursor = new MatrixCursor(new String[] {
//                COL_VIDEO_ID,
//                COL_VIDEO_TITLE,
//                COL_VIDEO_URL,
//                COL_STREAM_TYPE
//        });
//        
//        if (playlist != null) {
//            for (PlaylistItem item : playlist) {
//                cursor.addRow(new String[] {
//                        item.getVideoId(),
//                        item.getTitle(),
//                        item.getUrl(),
//                        Integer.toString(item.getStreamType())
//                });
//            }    
//        }
//        
//        return cursor;
//    }
    
    /**
     * Implement this method to return an item to be played in OVO launcher.
     * 
     * OVO launcher call this method when it is launched and is ready to 
     * play something.
     * 
     * You should always try to return a non-null item, so there will be 
     * always something to be played in OVO launcher. If this method 
     * returned null, OVO launcher will retry by calling this method  
     * again after few seconds.
     * 
     * @return A playlist item to be played in launcher.
     */
    protected abstract ProviderPlaylistItem getLastPlayedItem();
    
    private Cursor queryLastPlayedItem() {
        ProviderPlaylistItem item = getLastPlayedItem();
        MatrixCursor cursor = new MatrixCursor(new String[] {
                COL_VIDEO_ID,
                COL_VIDEO_TITLE,
                COL_VIDEO_URL,
                COL_STREAM_TYPE
        });
        
        if (item != null) {
            cursor.addRow(new String[] {
                    item.getVideoId(),
                    item.getTitle(),
                    item.getUrl(),
                    Integer.toString(item.getStreamType())
            });
            return cursor;
        }
        
        return null;
    }
    
    /**
     * Implement this method to return the next item in playlist based 
     * on the given video ID.
     * 
     * When OVO launcher reaches the end of the current video item, 
     * it calls this method to get the next item to play. 
     * 
     * You should always try to return a non-null item. If this method 
     * returned null, OVO launcher will stop playback until the home page
     * in OVO launcher is reloaded.
     * 
     * @param videoId The ID of the current video item played in OVO launcher.
     * @return The next item in playlist.
     */
    protected abstract ProviderPlaylistItem getNextItemInPlaylist(String videoId);
    
    /*
     * videoId here and PlaylistItem.getVideoId() are two different things.
     */
    private Cursor queryNextItemInPlaylist(String videoId) {
        ProviderPlaylistItem nextItem = getNextItemInPlaylist(videoId);
        MatrixCursor cursor = new MatrixCursor(new String[] {
                COL_VIDEO_ID,
                COL_VIDEO_TITLE,
                COL_VIDEO_URL,
                COL_STREAM_TYPE
        });
        
        if (nextItem != null) {
            cursor.addRow(new String[] {
                    nextItem.getVideoId(),
                    nextItem.getTitle(),
                    nextItem.getUrl(),
                    Integer.toString(nextItem.getStreamType())
            });
            return cursor;
        }
        
        return null;
    }
    
    /**
     * Implement this method to return the previous item in playlist based 
     * on the given video ID.
     * 
     * This method basically will not be called unless we add more 
     * advanced playback functions.
     * 
     * @param videoId The ID of the current video item played in OVO launcher.
     * @return The previous item in playlist.
     */
    protected abstract ProviderPlaylistItem getPrevItemInPlaylist(String videoId);
    
    /*
     * videoId here and PlaylistItem.getVideoId() are two different things.
     */
    private Cursor queryPrevItemInPlaylist(String videoId) {
        ProviderPlaylistItem prevItem = getPrevItemInPlaylist(videoId);
        MatrixCursor cursor = new MatrixCursor(new String[] {
                COL_VIDEO_ID,
                COL_VIDEO_TITLE,
                COL_VIDEO_URL,
                COL_STREAM_TYPE
        });
        
        if (prevItem != null) {
            cursor.addRow(new String[] {
                    prevItem.getVideoId(),
                    prevItem.getTitle(),
                    prevItem.getUrl(),
                    Integer.toString(prevItem.getStreamType())
            });
            return cursor;
        }
        
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, 
            String[] selectionArgs) {
        int id = mUriMatcher.match(uri);
        
        switch(id) {
        case ID_SET_LAST_PLAYED_ITEM:
            setLastPlayedItem(uri.getLastPathSegment());
            break;
        case ID_REPORT_ITEM_WATCHING:
            reportWatching(uri.getLastPathSegment());
            break;
        case ID_REPORT_ITEM_WATCHED:
            reportWatched(uri.getLastPathSegment());
            break;
        case ID_REPORT_ITEM_CONTINUE_WATCHING:
            reportContinueWatching(uri.getLastPathSegment());
            break;
        case ID_REPORT_ITEM_SKIPPED:
            reportSkipped(uri.getLastPathSegment());
            break;
        }
        
        return 0;
    }
    
    /**
     * When OVO launcher starts a video item, it call this method to 
     * report what it is playing. 
     * 
     * You should save this information and return it in 
     * 'getLastPlayedItem()', so OVO launcher can start playback from 
     * where it stop last time.
     * 
     * @param videoId The ID of the current video item played in OVO launcher.
     */
    protected abstract void setLastPlayedItem(String videoId);
    
    /**
     * OVO launcher reports a watching event by calling this method 
     * when a video item is started.
     * 
     * @param videoId The ID of the video item about to start.
     */
    protected abstract void reportWatching(String videoId);
    
    /**
     * OVO launcher reports a watched event by calling this method 
     * when it reaches the end of a video item.
     * 
     * @param videoId The ID of the ended video item.
     */
    protected abstract void reportWatched(String videoId);
    
    /**
     * OVO launcher reports continue events by calling this method 
     * periodically while it is playing a live stream.
     * 
     * @param videoId The ID of the video item. 
     */
    protected abstract void reportContinueWatching(String videoId);
    
    /**
     * OVO launcher reports skipped event by calling this method when 
     * a video item is stopped before the end of this item is reached.
     * 
     * @param videoId The ID of the stopped video item.
     */
    protected abstract void reportSkipped(String videoId);

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
