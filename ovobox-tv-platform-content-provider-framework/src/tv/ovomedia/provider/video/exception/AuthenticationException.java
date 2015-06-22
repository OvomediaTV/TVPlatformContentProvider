/**
 * Authentication Exception.
 * Application can throw this exception when user is not authorized.
 * 
 * @author zackchiu@ovomedia.tv
 * @copyright Copyright (c) 2015, OVOMEDIA CREATIVE INC.
 */
package tv.ovomedia.provider.video.exception;

public class AuthenticationException extends Exception {
    private static final long serialVersionUID = 1L;

    private AuthenticationException() {
        super();
    }
}
