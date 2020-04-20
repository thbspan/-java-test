package com.test.parsing;

@FunctionalInterface
public interface TokenHandler {

  String handleToken(String content);
}