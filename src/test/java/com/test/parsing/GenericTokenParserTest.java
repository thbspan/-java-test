package com.test.parsing;

import org.junit.Test;

public class GenericTokenParserTest {

    @Test
    public void test() {
        final GenericTokenParser parser = new GenericTokenParser("${", "}", content -> content);
        System.out.println(parser.parseWithEscapeChar("${dfdsf}"));
    }
}
