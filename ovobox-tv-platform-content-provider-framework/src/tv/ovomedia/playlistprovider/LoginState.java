package tv.ovomedia.playlistprovider;

public class LoginState {
    private boolean mIsLoggedIn = false;
    private String mPackageName = null;
    private String mLoginActivityClassName = null;
    
    public LoginState(boolean isLoggedIn) {
        mIsLoggedIn = isLoggedIn;
    }
    
    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }
}
