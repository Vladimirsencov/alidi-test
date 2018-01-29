package com.alidi.test.parser;

public interface NumberToStringTwoWayParser {
    String parseNumberToString(long input);

    long parseToNumber(String str);
}
