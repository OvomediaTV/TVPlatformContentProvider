package tv.ovomedia.playlistprovider;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Binder;

public class PermissionChecker {
    private static final int SYSTEM_UID = 1000;
    private static final ArrayList<String> ALLOWED_PACKAGES;
    private static final ArrayList<String> ALLOWED_SIGNATURES;
    
    static {
        ALLOWED_PACKAGES = new ArrayList<String>();
        ALLOWED_PACKAGES.add("tv.ovomedia.launcher");
        ALLOWED_PACKAGES.add("com.example.ovo.content.provider.test");
        
        ALLOWED_SIGNATURES = new ArrayList<String>();
        ALLOWED_SIGNATURES.add("f7d4907b"); // ovo key
        ALLOWED_SIGNATURES.add("b4addb29"); // old platform key
        ALLOWED_SIGNATURES.add("b011ca1"); // platform key
    };

    /**
     * Check the signature of the caller package.
     * This must be called in a method which is called from outside 
     * via a binder, otherwise it cannot get the caller's package name 
     * correctly.
     * @return True is the caller's signature is allowed, false otherwiase. 
     */
    public static boolean checkSignature(Context context) {
        String callerPackage = context.getPackageManager()
                .getNameForUid(Binder.getCallingUid());
        
        // If caller's UID is system's UID, just pass it.
        if (Binder.getCallingUid() == SYSTEM_UID) {
            return true;
        }
        
        if (callerPackage == null) {
            // Unknown caller.
            return false;
        }
        
        if (ALLOWED_PACKAGES.contains(callerPackage) == false) {
            // Package is not allowed.
            return false;
        }
        
        try {
            Signature[] signatures = context.getPackageManager()
                    .getPackageInfo(
                            callerPackage, 
                            PackageManager.GET_SIGNATURES)
                    .signatures;
            
            for (Signature sig : signatures) {
                String hash = Integer.toHexString(sig.hashCode());
                
                if (ALLOWED_SIGNATURES.contains(hash) == true) {
                    return true;
                }
            }
        } catch (NameNotFoundException e) {
            // Fail to get signature.
        }
        
        return false;
    }
}
