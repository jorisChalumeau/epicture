package eu.epitech.spartan.epicture.modele;

/**
 * to store user tokens and app client-id
 */
public class ImgurSettings {
    public final static String IMGUR_CLIENT_ID = "f4b1b225b0c412a";
    public final static String IMGUR_CLIENT_SECRET = "7928201b1864f682f151cf7e87862df938627915";
    private static String accessToken = null;
    private static String refreshToken = null;
    private static boolean authenticated = false;

    /**
     * user's account tokens are used
     */
    public static void authenticate(String accessToken, String refreshToken) {
        setAuthenticated(true);
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
    }

    public static void deauthenticate() {
        setAuthenticated(false);
        setAccessToken(null);
        setRefreshToken(null);
    }

    public static String getAccessToken() {
        return accessToken;
    }

    private static void setAccessToken(String accessToken) {
        ImgurSettings.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    private static void setRefreshToken(String refreshToken) {
        ImgurSettings.refreshToken = refreshToken;
    }

    public static boolean isAuthenticated() {
        return authenticated;
    }

    private static void setAuthenticated(boolean authenticated) {
        ImgurSettings.authenticated = authenticated;
    }
}
