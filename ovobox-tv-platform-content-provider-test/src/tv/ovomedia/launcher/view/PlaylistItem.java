package tv.ovomedia.launcher.view;

public class PlaylistItem {
    public static final int STREAM_TYPE_FILE = 100;
    public static final int STREAM_TYPE_LIVE = 150;
    private String mVideoId = null;
    private String mTitle = null;
    private String mUrl = null;
    private int mStreamType = STREAM_TYPE_FILE;
    private String mAuthority = null;
    
    public PlaylistItem(String videoId, String title, String url, 
            int streamType, String authority) {
        mVideoId = videoId;
        mTitle = title;
        mUrl = url;
        mStreamType = streamType;
        mAuthority = authority;
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
    
    public String getAuthority() {
        return mAuthority;
    }
}