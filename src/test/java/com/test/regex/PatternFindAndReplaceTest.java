package com.test.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PatternFindAndReplaceTest {

    @Test
    public void test() {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("${env1}", "1");
        replacements.put("${env2}", "2");
        replacements.put("${env3}", "3");

        String line = "${env4}sojods${env2}${env3}-${env1}";

        Pattern pattern = Pattern.compile("(\\$\\{[^}]+\\})");
        StringBuilder builder = new StringBuilder();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String replacement = replacements.get(matcher.group(1));
            if (replacement != null) {
                matcher.appendReplacement(builder, replacement);
            }
        }
        matcher.appendTail(builder);
        System.out.println(builder);
    }
}
