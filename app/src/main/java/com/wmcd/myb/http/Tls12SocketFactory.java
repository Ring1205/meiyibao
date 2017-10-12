package com.wmcd.myb.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by Administrator on 2017/5/18.
 */
public class Tls12SocketFactory extends SSLSocketFactory {
    /**
     * The constant TLS_SUPPORT_VERSION.
     */
    private static final String[] TLS_SUPPORT_VERSION = {"TLSv1.1", "TLSv1.2"};

    /**
     * The Delegate.
     */
    final SSLSocketFactory delegate;

    /**
     * Instantiates a new Tls 12 socket factory.
     *
     * @param tm the tm
     */
    public Tls12SocketFactory(TrustManager[] tm)  {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        this.delegate = sslContext.getSocketFactory();
    }

    /**
     * Get default cipher suites string [ ].
     *
     * @return the string [ ]
     */
    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    /**
     * Get supported cipher suites string [ ].
     *
     * @return the string [ ]
     */
    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    /**
     * Create socket socket.
     *
     * @param s         the s
     * @param host      the host
     * @param port      the port
     * @param autoClose the auto close
     * @return the socket
     * @throws IOException the io exception
     */
    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return patch(delegate.createSocket(s, host, port, autoClose));
    }

    /**
     * Create socket socket.
     *
     * @param host the host
     * @param port the port
     * @return the socket
     * @throws IOException          the io exception
     * @throws UnknownHostException the unknown host exception
     */
    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return patch(delegate.createSocket(host, port));
    }

    /**
     * Create socket socket.
     *
     * @param host      the host
     * @param port      the port
     * @param localHost the local host
     * @param localPort the local port
     * @return the socket
     * @throws IOException          the io exception
     * @throws UnknownHostException the unknown host exception
     */
    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return patch(delegate.createSocket(host, port, localHost, localPort));
    }

    /**
     * Create socket socket.
     *
     * @param host the host
     * @param port the port
     * @return the socket
     * @throws IOException the io exception
     */
    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return patch(delegate.createSocket(host, port));
    }

    /**
     * Create socket socket.
     *
     * @param address      the address
     * @param port         the port
     * @param localAddress the local address
     * @param localPort    the local port
     * @return the socket
     * @throws IOException the io exception
     */
    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return patch(delegate.createSocket(address, port, localAddress, localPort));
    }

    /**
     * Patch socket.
     *
     * @param s the s
     * @return the socket
     */
    private Socket patch(Socket s) {
        if (s instanceof SSLSocket) {
            ((SSLSocket) s).setEnabledProtocols(TLS_SUPPORT_VERSION);
        }
        return s;
    }
}
