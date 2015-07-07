package tv.ovomedia.playlistprovider;

/**
 * This class provides a standard data format between AbsPlaylistProvider 
 * and its resolver.
 * 
 * @author lunker
 *
 */
public class ProviderPlaylistItem {
    public static final int STREAM_TYPE_FILE = 100;
    public static final int STREAM_TYPE_LIVE = 150;
    
    private String mVideoId = null;
    private String mTitle = null;
    private String mUrl = null;
    private int mStreamType = STREAM_TYPE_FILE;
    
    /**
     * Constructor. 
     * 
     * @param videoId ID of the video item. It is a string, so you can 
     * put anything except '/' into it. OVO launcher will use this ID 
     * to query last/next/prev item in playlist and report events.
     * @param title Title of this video item.
     * @param url URL of this video item.
     * @param streamType Stream type of this video item. It can be  
     * STREAM_TYPE_FILE or STREAM_TYPE_LIVE. OVO launcher does not report 
     * 'continue' event if the video item it is playing is not a live stream.
     */
    public ProviderPlaylistItem(String videoId, String title, String url, 
            int streamType) {
        mVideoId = videoId;
        mTitle = title;
        mUrl = url;
        mStreamType = streamType;
    }
    
    public String getVideoId() {
        return mVideoId;
    }
    
    public String getTitle() {
        return mTitle;
    }
    
    public String getUrl() {
        return mUrl;
    }
    
    public int getStreamType() {
        return mStreamType;
    }
    
}