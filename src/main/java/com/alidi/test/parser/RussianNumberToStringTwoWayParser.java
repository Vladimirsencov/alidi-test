package com.alidi.test.parser;

import org.springframework.stereotype.Component;

import java.util.Map;

import static com.example.test.util.RussianParserHelper.*;
import static java.lang.Long.sum;
import static java.util.Collections.emptyMap;

@Component
public class RussianNumberToStringTwoWayParser implements NumberToStringTwoWayParser {


    @Override
    public String parseNumberToString(long input) {
        if (input < 0 || input > 999_999_999_999L) {
            throw new IllegalArgumentException("input value is negative or to large");
        }
        StringBuilder acc = new StringBuilder();
        if (input == 0) {
            return "ноль";
        }
        parseToNumber(input, acc);
        return normalize(acc.toString());
    }

    private String normalize(String str) {
        return str.replaceAll("один тысяча", "одна тысяча")
                .replaceAll("два тысячи", "две тысячи")
                .trim();
    }

    private String deNormalize(String str) {
        return str.replaceAll("одна тысяча", "один тысяча")
                .replaceAll("две тысячи", "два тысячи")
                .trim();
    }

    private void parseToNumber(long input, StringBuilder acc) {

        if (input == 0) return;

        int range = getRange(input);
        switch (range) {
            case 12:
            case 11:
            case 10:
                parseBillions(input, acc);
                break;
            case 9:
            case 8:
            case 7:
                parseMillions(input, acc);
                break;
            case 6:
            case 5:
            case 4:
                parseThousands(input, acc);
                break;
            case 3:
                parseHundreds(input, acc);
                break;
            case 2:
                if (input > 19) {
                    parseDecimals(input, acc);

                } else {
                    parseUnit(input, acc);
                }
                break;
            case 1:
                parseUnit(input, acc);
                break;
        }
    }


    private void parseBillions(long input, StringBuilder acc) {
        long billion = 1_000_000_000;
        basicParse(input / billion, acc, BILLION_FORMS);
        parseToNumber(input % billion, acc);
    }

    private void parseMillions(long input, StringBuilder acc) {
        long million = 1_000_000;
        basicParse(input / million, acc, MILLIONS_FORMS);
        parseToNumber(input % million, acc);
    }

    private void parseThousands(long input, StringBuilder acc) {
        long thousand = 1000;
        basicParse(input / thousand, acc, THOUSANDS_FORMS);
        parseToNumber(input % thousand, acc);
    }

    private void parseHundreds(long input, StringBuilder acc) {
        basicParse(input, acc, emptyMap());
        parseToNumber(0L, acc);
    }

    private void parseDecimals(long input, StringBuilder acc) {
        basicParse(input, acc, emptyMap());
        parseToNumber(0L, acc);
    }

    private void parseUnit(long input, StringBuilder acc) {
        basicParse(input, acc, emptyMap());
        parseToNumber(0L, acc);
    }

    private void basicParse(long number, StringBuilder acc, Map<Long, String> wordForm) {
        int range = getRange(number);
        switch (range) {
            case 1:
                handleOneRange((int) number, acc, wordForm);
                break;
            case 2:
                handleDoubleRange((int) number, acc, wordForm);
                break;
            case 3:
                handleThreeRange((int) number, acc, wordForm);
                break;
        }
    }


    private void handleThreeRange(int number, StringBuilder acc, Map<Long, String> wordForm) {
        String str = Integer.valueOf(number).toString();
        Long oldRange = Long.valueOf(str.substring(0, 1));
        Long middleRange = Long.valueOf(str.substring(1, 2));
        Long smallestRange = Long.valueOf(str.substring(2, 3));

        acc.append(HUNDREDS.get(oldRange))
                .append(UNIT_BLANK);

        if (middleRange == 0L && smallestRange == 0L) {
            acc.append(wordForm.getOrDefault(smallestRange, ""))
                    .append(UNIT_BLANK);
        } else if (middleRange == 0 || middleRange == 1L) {
            acc.append(ONES.get(10L * middleRange + smallestRange))
                    .append(UNIT_BLANK)
                    .append(wordForm.getOrDefault(smallestRange, ""))
                    .append(UNIT_BLANK);
        } else {
            handleDoubleRange((int) (10L * middleRange + smallestRange), acc, wordForm);
        }
    }

    private void handleDoubleRange(int number, StringBuilder acc, Map<Long, String> wordForm) {
        String str = Integer.valueOf(number).toString();
        Long oldRange = Long.valueOf(str.substring(0, 1));
        Long smallestRange = Long.valueOf(str.substring(1, 2));
        if (oldRange == 1L) {
            acc.append(ONES.get(sum(10L, smallestRange)))
                    .append(UNIT_BLANK)
                    .append(wordForm.getOrDefault(smallestRange, ""))
                    .append(UNIT_BLANK);

        } else {
            acc.append(DECIMALS.get(oldRange))
                    .append(UNIT_BLANK);
            if (smallestRange == 0L) {
                acc
                        .append(wordForm.getOrDefault(smallestRange, ""))
                        .append(UNIT_BLANK);
            } else {
                acc
                        .append(ONES.get(smallestRange))
                        .append(UNIT_BLANK)
                        .append(wordForm.getOrDefault(smallestRange, ""))
                        .append(UNIT_BLANK);
            }
        }
    }

    private void handleOneRange(int number, StringBuilder acc, Map<Long, String> wordForm) {
        acc.append(ONES.get((long) number))
                .append(UNIT_BLANK)
                .append(wordForm.getOrDefault((long) number, ""))
                .append(UNIT_BLANK);
    }

    private int getRange(long input) {
        return Long.valueOf(input).toString().length();
    }

    @Override
    public long parseToNumber(String str) {
        str = str.trim().replaceAll("[ /s]+", " ");
        str = deNormalize(str);
        long acc[] = new long[1];
        if (str.equals("ноль")) {
            return 0;
        }
        parseToNumber(acc, str);
        return acc[0];
    }

    private void parseToNumber(long[] acc, String str) {
        str = str.trim();
        if (str.matches(BILLION_PATTERN)) {
            restoreBillions(acc, str);
        } else if (str.matches(MILLION_PATTERN)) {
            restoreMillions(acc, str);
        } else if (str.matches(THOUSAND_PATTERN)) {
            restoreThousands(acc, str);
        } else {
            baseRestoreNumber(acc, str, 1);
        }
    }

    private void restoreBillions(long[] acc, String str) {
        String[] billionsAndMore = str.split(getSplitArgument(str, BILLION_WORD_FORM));
        baseRestoreNumber(acc, billionsAndMore[0], 1_000_000_000);
        if (billionsAndMore.length != 1) {
            parseToNumber(acc, billionsAndMore[1]);
        }
    }

    private void restoreMillions(long[] acc, String str) {
        String[] millionsAndMore = str.split(getSplitArgument(str, MILLION_WORD_FORM));
        baseRestoreNumber(acc, millionsAndMore[0], 1_000_000);
        if (millionsAndMore.length != 1) {
            parseToNumber(acc, millionsAndMore[1]);
        }
    }

    private void restoreThousands(long[] acc, String str) {
        String[] thousandAndMore = str.split(getSplitArgument(str, THOUSAND_WORD_FORM));
        baseRestoreNumber(acc, thousandAndMore[0], 1_000);
        if (thousandAndMore.length != 1) {
            parseToNumber(acc, thousandAndMore[1]);
        }
    }

    private void baseRestoreNumber(long[] acc, String str, long multiplex) {
        String arr[] = str.split(" ");
        switch (arr.length) {
            case 3:
                handleThreeWord(acc, arr, multiplex);
                break;
            case 2:
                handleTwoWord(acc, arr, multiplex);
                break;
            case 1:
                handleOneWord(acc, arr[0], multiplex);
                break;
            default:
                throw new IllegalArgumentException("invalid string parameter:" + str);
        }
    }

    private void handleThreeWord(long[] acc, String[] arr, long multiplex) {
        acc[0] += (HUNDREDS.inverse().get(arr[0]) * 100 * multiplex);
        arr = new String[]{arr[1], arr[2]};
        handleTwoWord(acc, arr, multiplex);
    }

    private void handleTwoWord(long[] acc, String[] arr, long multiplex) {
        if (HUNDREDS.containsValue(arr[0])) {
            acc[0] += (HUNDREDS.inverse().get(arr[0]) * 100 * multiplex);
            handleOneWord(acc, arr[1], multiplex);
        } else {
            acc[0] += (DECIMALS.inverse().get(arr[0]) * 10 * multiplex);
            handleOneWord(acc, arr[1], multiplex);
        }
    }

    private void handleOneWord(long[] acc, String arr, long multiplex) {
        if (HUNDREDS.containsValue(arr)) {
            acc[0] += (HUNDREDS.inverse().get(arr) * 100 * multiplex);
        } else if (DECIMALS.containsValue(arr)) {
            acc[0] += (DECIMALS.inverse().get(arr) * 10 * multiplex);
        } else {
            acc[0] += (ONES.inverse().get(arr) * multiplex);
        }
    }

    private String getSplitArgument(String str, String... args) {
        if (str.contains(args[0])) {
            return args[0];
        }
        if (str.contains(args[1])) {
            return args[1];
        }
        if (str.contains(args[2])) {
            return args[2];
        }
        throw new IllegalArgumentException("Not valid argument");
    }

}
