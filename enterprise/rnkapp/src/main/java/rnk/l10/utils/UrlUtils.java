package rnk.l10.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {
    public static String getUrl(HttpServletRequest req){
        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        return url.toString();
    }

    public static String getLocalUrl(HttpServletRequest req){
        String scheme = req.getScheme();             // http
//        String serverName = req.getServerName();     // hostname.com
        String serverName = "localhost";     // hostname.com
//        int serverPort = req.getServerPort();        // 80
        int serverPort =8080;        // 80

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        return url.toString();
    }
}
