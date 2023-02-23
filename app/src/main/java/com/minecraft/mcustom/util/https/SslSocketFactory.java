package com.minecraft.mcustom.util.https;

import khandroid.ext.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


class SslSocketFactory extends SSLSocketFactory {
    public SslSocketFactory(InputStream keyStore, String keyStorePassword) throws GeneralSecurityException {
        super(createSSLContext(keyStore, keyStorePassword), STRICT_HOSTNAME_VERIFIER);
    }


    private static SSLContext createSSLContext(InputStream keyStore, String keyStorePassword) throws GeneralSecurityException {
        SSLContext sslcontext;
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { new SsX509TrustManager(keyStore, keyStorePassword) }, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IllegalStateException("Failure initializing default SSL context", e);
        }

        return sslcontext;
    }
}