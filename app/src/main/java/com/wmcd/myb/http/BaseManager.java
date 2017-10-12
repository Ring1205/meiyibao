package com.wmcd.myb.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.wmcd.myb.util.DateUtil;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 邓志宏 on 2017/2/11.
 */
public class BaseManager {
    /**
     * The constant key.
     */
    private static String key = "0E7C70790BD3BC87626828CCFB205465";
    /**
     * The constant salt.
     */
    private static String salt = "1C4C91AB3D0BEC43DF86746B79709EFA";

    /**
     * The type Http holder.
     */
    private static class HttpHolder {

        /**
         * The constant timestamp.
         */
        private static String timestamp;
        /**
         * The constant client.
         */
//        private static SSLSocketFactory socketFactory = new Tls12SocketFactory(new TrustManager[]{new MyHttpsUtils.UnSafeTrustManager()});
        private static OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
//                .sslSocketFactory(socketFactory,new MyHttpsUtils.UnSafeTrustManager())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        String allString = "";
                        //做数据合法性校验
                        Map<String, String> parameterMap = parseParams(request);
                        if (null != parameterMap && !parameterMap.isEmpty()) {
                            List<String> paramList = new ArrayList<>();
                            for (String key : parameterMap.keySet()) {
                                paramList.add(key + "=" + parameterMap.get(key));
                            }
                            Collections.sort(paramList, String.CASE_INSENSITIVE_ORDER);
                            allString = getAllString(paramList, "&");
                        }
                        timestamp = DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS");
                        Log.e("Base-------",timestamp+"");
                        Request.Builder builder1 = request.newBuilder();
                        String token = new String(Hex.encodeHex(DigestUtils.md5((key.concat(timestamp).concat(allString).concat(salt).getBytes("UTF-8")))));
//                        MD5.getMessageDigest((key + timestamp + salt).getBytes("UTF-8"))
                        Request build = builder1.addHeader("token",token )
                                .addHeader("timestamp", timestamp)
                                .build();
                        return chain.proceed(build);
                    }
                }).retryOnConnectionFailure(true)
                .build();
        /**
         * The constant singleton.
         */
        private static Retrofit singleton = new Retrofit.Builder()
                .client(client)
                .baseUrl(UrlConfig.IP)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm").registerTypeAdapter(Timestamp.class, new TimeTypeAdapter()).create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 解析请求参数
     *
     * @param request the request
     * @return map
     */
    public static Map<String, String> parseParams(Request request) {
        //GET POST DELETE PUT PATCH
        String method = request.method();
        Map<String, String> params = null;
        if ("GET".equals(method)) {
            params = doGet(request);
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            RequestBody body = request.body();
            if (body != null && body instanceof FormBody) {
                params = doForm(request);
            } else {
                params = doGet(request);
            }
        }
        return params;
    }

    /**
     * 获取get方式的请求参数
     *
     * @param request the request
     * @return map
     */
    private static Map<String, String> doGet(Request request) {
        Map<String, String> params = null;
        HttpUrl url = request.url();
        Set<String> strings = url.queryParameterNames();
        if (strings != null) {
            Iterator<String> iterator = strings.iterator();
            params = new HashMap<>();
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = url.queryParameterValue(i);
                params.put(name, value);
                i++;
            }
        }
        return params;
    }

    /**
     * 获取表单的请求参数
     *
     * @param request the request
     * @return map
     */
    private static Map<String, String> doForm(Request request) {
        Map<String, String> params = null;
        FormBody body = null;
        try {
            body = (FormBody) request.body();
        } catch (ClassCastException c) {
        }
        if (body != null) {
            int size = body.size();
            if (size > 0) {
                params = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    params.put(body.name(i), body.value(i));
                }
            }
        }
        return params;
    }


    /**
     * The constant singleton.
     */
    private static Retrofit singleton = HttpHolder.singleton;

    /**
     * Gets file key.
     *
     * @param key      参数的key
     * @param fileName 文件的名字
     * @return file key
     */
    public static String getFileKey(String key, String fileName) {
        return key + "\"; filename=\"" + fileName + ".png";
    }

    /**
     * Create body request body.
     *
     * @param file the file
     * @return the request body
     */
    public static RequestBody createBody(File file) {
        RequestBody fBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return fBody;
    }

    /**
     * Create body request body.
     *
     * @param str the str
     * @return the request body
     */
    public static RequestBody createBody(String str) {
        RequestBody fBody = RequestBody.create(MediaType.parse("text/plain"), str);
        return fBody;
    }

    /**
     * Create t.
     *
     * @param <T>     the type parameter
     * @param context the context
     * @param clazz   the clazz
     * @return the t
     */
    public static <T> T create(Context context, Class<T> clazz) {
        return singleton.create(clazz);
    }

    /**
     * Sets subscribe.
     *
     * @param <T>        the type parameter
     * @param observable the observable
     * @param observer   the observer
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }


    /**
     * Gets all string.
     *
     * @param collection the collection
     * @param split      the split
     * @return the all string
     */
    public static String getAllString(List<String> collection, String split) {
        if (null != collection) {
            String str = new String();
            for (String s : collection) {
                str += s + split;
            }
            return str.replaceAll("\u0000", "").substring(0, str.length() - 1);
        }
        return null;
    }
}
