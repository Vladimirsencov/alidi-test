package com.example.test.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class RussianParserHelper {

    public static final String UNIT_BLANK = " ";
    public static final String BILLION_PATTERN = "(.*)миллиард(.*)";
    public static final String MILLION_PATTERN = "(.*)миллион(.*)";
    public static final String THOUSAND_PATTERN = "(.*)тысяч(.*)";
    public static final String[] BILLION_WORD_FORM = {"миллиардов", "миллиарда", "миллиард"};
    public static final String[] MILLION_WORD_FORM = {"миллионов", "миллиона", "миллион"};
    public static final String[] THOUSAND_WORD_FORM = {"тысяча", "тысячи", "тысяч"};
    public static final BiMap<Long, String> ONES = ImmutableBiMap.<Long, String>builder()
            .put(0L, "")
            .put(1L, "один")
            .put(2L, "два")
            .put(3L, "три")
            .put(4L, "четыре")
            .put(5L, "пять")
            .put(6L, "шесть")
            .put(7L, "семь")
            .put(8L, "восемь")
            .put(9L, "девять")
            .put(10L, "десять")
            .put(11L, "одиннадцать")
            .put(12L, "двенадцать")
            .put(13L, "тринадцать")
            .put(14L, "четырнадцать")
            .put(15L, "пятнадцать")
            .put(16L, "шестнадцать")
            .put(17L, "семнадцать")
            .put(18L, "восемнадцать")
            .put(19L, "девятнадцать")
            .build();
    public static final BiMap<Long, String> DECIMALS = ImmutableBiMap.<Long, String>builder()
            .put(2L, "двадцать")
            .put(3L, "тридцать")
            .put(4L, "сорок")
            .put(5L, "пятьдесят")
            .put(6L, "шестьдесят")
            .put(7L, "семьдесят")
            .put(8L, "восемьдесят")
            .put(9L, "девяносто")
            .build();
    public static final BiMap<Long, String> HUNDREDS = ImmutableBiMap.<Long, String>builder()
            .put(0L, "")
            .put(1L, "сто")
            .put(2L, "двести")
            .put(3L, "триста")
            .put(4L, "четыреста")
            .put(5L, "пятьсот")
            .put(6L, "шестьсот")
            .put(7L, "семьсот")
            .put(8L, "восемьсот")
            .put(9L, "девятьсот")
            .build();
    public static final Map<Long, String> THOUSANDS_FORMS = ImmutableMap.<Long, String>builder()
            .put(1L, "тысяча")
            .put(2L, "тысячи")
            .put(3L, "тысячи")
            .put(4L, "тысячи")
            .put(5L, "тысяч")
            .put(6L, "тысяч")
            .put(7L, "тысяч")
            .put(8L, "тысяч")
            .put(9L, "тысяч")
            .put(0L, "тысяч")
            .build();
    public static final Map<Long, String> MILLIONS_FORMS = ImmutableMap.<Long, String>builder()
            .put(1L, "миллион")
            .put(2L, "миллиона")
            .put(3L, "миллиона")
            .put(4L, "миллиона")
            .put(5L, "миллионов")
            .put(6L, "миллионов")
            .put(7L, "миллионов")
            .put(8L, "миллионов")
            .put(9L, "миллионов")
            .put(0L, "миллионов")
            .build();
    public static final Map<Long, String> BILLION_FORMS = ImmutableMap.<Long, String>builder()
            .put(1L, "миллиард")
            .put(2L, "миллиарда")
            .put(3L, "миллиарда")
            .put(4L, "миллиарда")
            .put(5L, "миллиардов")
            .put(6L, "миллиардов")
            .put(7L, "миллиардов")
            .put(8L, "миллиардов")
            .put(9L, "миллиардов")
            .put(0L, "миллиардов")
            .build();

    private RussianParserHelper() {
    }

}
