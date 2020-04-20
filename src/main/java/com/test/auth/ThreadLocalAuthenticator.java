package com.test.auth;

import java.net.Authenticator;

public class ThreadLocalAuthenticator extends Authenticator {
    private static final ThreadLocal<String> proxy_password = new ThreadLocal<>();
    private static final ThreadLocal<String> proxy_username = new ThreadLocal<>();
    private static final ThreadLocal<String> server_password = new ThreadLocal<>();
    private static final ThreadLocal<String> server_username = new ThreadLocal<>();

    private static final ThreadLocalAuthenticator threadAuthenticator = new ThreadLocalAuthenticator();

    public static ThreadLocalAuthenticator getAuthenticator() {
        return threadAuthenticator;
    }

    public static void setAsDefault() {
        setDefault(threadAuthenticator);
    }
}
