package com.test.parsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenericTokenParserTest {

    @Test
    public void test() {
        final GenericTokenParser parser = new GenericTokenParser("${", "}", content -> content);
        Assertions.assertEquals(parser.parseWithEscapeChar("${dfdsf}"), "dfdsf");
        Assertions.assertEquals(parser.parseWithEscapeChar("${jack}"), "jack");
    }
}
