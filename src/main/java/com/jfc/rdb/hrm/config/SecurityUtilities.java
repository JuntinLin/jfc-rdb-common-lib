package com.jfc.rdb.hrm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
@Profile("!ext")   // 舊 HRM MSSQL TLSv1 hack：會毒害全 JVM 預設 SSLContext（Java 21 停用 TLSv1 → 所有 TLS 全滅），ext 不連 MSSQL 一律排除
public class SecurityUtilities {

    @PostConstruct
    public void configureSSL() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
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
            SSLContext sc = SSLContext.getInstance("TLSv1");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            SSLContext.setDefault(sc);

            // Enable all available protocols
            //System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            //System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
         // Enable ONLY TLS 1.0
            System.setProperty("jdk.tls.client.protocols", "TLSv1");
            System.setProperty("https.protocols", "TLSv1");
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to configure SSL settings", e);
        }
    }
}