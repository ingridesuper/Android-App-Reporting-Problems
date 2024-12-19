package com.example.android_proiect_final_version.HttpsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class HttpsManager {
    private HttpsURLConnection httpsURLConnection;
    private BufferedReader bufferedReader;
    private String adresaURL;

    public HttpsManager(String adresaURL) {
        this.adresaURL = adresaURL;
    }

    private String getRezultat() throws IOException{
        trustEveryone();
        URL url=new URL(adresaURL);
        httpsURLConnection=(HttpsURLConnection) url.openConnection();
        bufferedReader=new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
        StringBuilder stringBuilder=new StringBuilder();
        String linie;
        while((linie=bufferedReader.readLine())!=null){
            stringBuilder.append(linie);
        }
        return stringBuilder.toString();
    }

    private void deconectare(){
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        httpsURLConnection.disconnect();
    }

    public String procesare(){
        try {
            return getRezultat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            deconectare();
        }
    }
    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
