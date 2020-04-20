package com.test.proxy;

public class UserServiceImpl implements UserService {

    @Override
    public void save(User user) {
        System.out.println("save user info");
    }

    @Override
    public void test() {
        System.out.println("test interface");
    }
}
