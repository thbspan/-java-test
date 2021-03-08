package com.test.hash;

import org.junit.jupiter.api.Test;

public class HashServiceTest {

    @Test
    public void testHash() {
        IHashService hashService = new HashService();
        System.out.println(hashService.hash("test"));
    }
}
