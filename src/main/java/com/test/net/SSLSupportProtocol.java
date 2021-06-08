package com.test.net;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLSupportProtocol {

    public static void main(String[] args) throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);

        SSLSocketFactory factory = context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket();

        String[] protocols = socket.getSupportedProtocols();

        System.out.println("Supported Protocols: " + protocols.length);
        for (String protocol : protocols) {
            System.out.println(" " + protocol);
        }

        protocols = socket.getEnabledProtocols();

        System.out.println("Enabled Protocols: " + protocols.length);
        for (String protocol : protocols) {
            System.out.println(" " + protocol);
        }

        String[] enabledCipherSuites = socket.getEnabledCipherSuites();
        System.out.println("Enabled cipher suites: " + enabledCipherSuites.length);

        for (String enabledCipherSuite : enabledCipherSuites) {
            System.out.println(" " + enabledCipherSuite);
        }
        // TLS_RSA_WITH_AES_256_CBC_SHA
        socket.setEnabledCipherSuites(new String[]{"TLS_RSA_WITH_AES_256_CBC_SHA"});
    }
}
