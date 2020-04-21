package be.rommens.zeus.poc;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:16
 */
public class UrlImageInputStream extends InputStream {

    private final String imageUrl;
    private HttpsURLConnection httpsURLConnection;
    private HttpURLConnection httpURLConnection;

    public UrlImageInputStream(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int read() throws IOException {
        if (httpsURLConnection != null) {
            return httpsURLConnection.getInputStream().read();
        }
        if (httpURLConnection != null) {
            return httpURLConnection.getInputStream().read();
        }
        return -1;
    }

    public void setSettings() throws IOException {
        URL url;
        url = new URL(imageUrl);

        if ("https".equals(url.getProtocol())) {
            setHttpsSettings(url);
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
    }

    private void setHttpsSettings(URL url) throws IOException {
        httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        httpsURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpsURLConnection.setInstanceFollowRedirects(true);
    }
}
