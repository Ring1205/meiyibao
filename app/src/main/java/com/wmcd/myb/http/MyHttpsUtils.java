package com.wmcd.myb.http;

import com.lzy.okhttputils.https.HttpsUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2017/5/18.
 */
public class MyHttpsUtils extends HttpsUtils {

    /**
     * The type Un safe trust manager.
     */
    public static class UnSafeTrustManager implements X509TrustManager {
        /**
         * Check client trusted.
         *
         * @param chain    the chain
         * @param authType the auth type
         * @throws CertificateException the certificate exception
         */
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        /**
         * Check server trusted.
         *
         * @param chain    the chain
         * @param authType the auth type
         * @throws CertificateException the certificate exception
         */
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        /**
         * Get accepted issuers x 509 certificate [ ].
         *
         * @return the x 509 certificate [ ]
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
