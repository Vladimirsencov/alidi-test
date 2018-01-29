package com.example.test.parser;

import com.alidi.test.parser.RussianNumberToStringTwoWayParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RussianNumberToStringTwoWayParser.class})
public class RussianNumberToStringTwoWayParserTest {

    private static Logger logger = LoggerFactory.getLogger(RussianNumberToStringTwoWayParserTest.class);
    @Autowired
    RussianNumberToStringTwoWayParser parser;

    @Test
    public void twoWayParsTest() {

        checkParse(1);
        checkParse(19);
        checkParse(29);
        checkParse(39);
        checkParse(100);
        checkParse(102);
        checkParse(119);
        checkParse(125);
        checkParse(925);
        checkParse(1000);
        checkParse(1001);
        checkParse(1019);
        checkParse(1119);
        checkParse(1999);
        checkParse(19999);
        checkParse(29999);
        checkParse(20999);
        checkParse(20919);
        checkParse(120919);
        checkParse(121919);
        checkParse(119000);
        checkParse(100_000_1255);
        checkParse(111_800_1255);
        checkParse(211_812_1255);

        checkParse(100_000_2255);
        checkParse(10_000_2255);
        checkParse(10_063_2255);
        checkParse(10_063_1255);

    }

    private void checkParse(long number) {
        String parseResult = parser.parseNumberToString(number);
        logger.info("input number is {} output string is {}", number, parseResult);
        Assert.assertEquals(number, parser.parseToNumber(parseResult));
    }
}