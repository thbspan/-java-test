package com.test.junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringStartsWith.startsWith;

public class ExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // @Test(expected = NullPointerException.class)
    @Test
    public void testNull() {
        String[] names = {"testA", "testB"};
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        thrown.expectMessage("2");
        thrown.expectMessage(startsWith("2"));
        System.out.println(names[2]);
    }
}
