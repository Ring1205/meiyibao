
package com.wmcd.myb.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by Administrator on 2016/4/5.
 */
public class SignUtils {
    /**
     * The constant ALGORITHM.
     */
    private static final String ALGORITHM = "RSA";

    /**
     * The constant SIGN_ALGORITHMS.
     */
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * The constant DEFAULT_CHARSET.
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Sign string.
     *
     * @param content    the content
     * @param privateKey the private key
     * @return the string
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM, "BC");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);


            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
