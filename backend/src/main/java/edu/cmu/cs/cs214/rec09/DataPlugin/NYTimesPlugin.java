package edu.cmu.cs.cs214.rec09.DataPlugin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.cmu.cs.cs214.rec09.framework.core.DataPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFramework;

public class NYTimesPlugin implements DataPlugin {
    private static final String PLUGIN_NAME = "New York Times";
    private TextAnalysisFramework framework;

    @Override
    public String getDataName() {
        return PLUGIN_NAME;
    }

    @Override
    public void onRegister(TextAnalysisFramework fw) {
        framework = fw;
        
    }

    @Override
    public List<String> processData(String data) {
        List<String> articleList = new ArrayList<>();
        String response=fetch("https://api.nytimes.com/svc/mostpopular/v2/viewed/"+data+".json?api-key=LUXWlqf0pJ4ezPbzwxIDDJjZnYqFEqHa", "TLSV1.3");
        // System.out.println(response);
        // parse json
        JSONObject json;
        JSONParser jsonParser=new JSONParser();
        try {
            json= (JSONObject) jsonParser.parse(response);
        } catch (ParseException e) {
            return null;
        }
        JSONArray articles = (JSONArray)json.get("results");

        Iterator<Object> itr = articles.iterator();
        while (itr.hasNext())
        {
            Map<String, String> article = (Map<String, String>) itr.next();
            articleList.add(article.get("abstract"));
        }
        return articleList;
    }


    private static void createTrustManager(String certType) throws KeyManagementException, NoSuchAlgorithmException{
        /**
         * Annoying SSLHandShakeException. After trying several methods, finally this
         * seemed to work.
         * Taken from: http://www.nakov.com/blog/2009/07/16/disable-certificate-validation-in-java-ssl-connections/
         */
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance(certType);
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
    private static String fetch(String searchURL, String certType) {
        try {
            // Create trust manager, which lets you ignore SSLHandshakeExceptions
            createTrustManager(certType);
        } catch (KeyManagementException ex) {
            System.out.println("Shouldn't come here: ");
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Shouldn't come here: ");
            ex.printStackTrace();
        }

        String response = "";
        try {
            URL url = new URL(searchURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Something wrong with URL");
            return null;
        }
        return response;
    }
    // test api 
    public static void main(String[] args) {
        
    }
}
