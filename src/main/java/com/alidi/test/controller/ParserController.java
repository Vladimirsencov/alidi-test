package com.alidi.test.controller;

import com.alidi.test.parser.NumberToStringTwoWayParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Function;

@RestController
@RequestMapping(path = "v1/parser")
public class ParserController {

    private static final Logger LOG = LoggerFactory.getLogger(ParserController.class);

    private final NumberToStringTwoWayParser parser;

    @Autowired
    public ParserController(NumberToStringTwoWayParser parser) {
        this.parser = parser;
    }

    @GetMapping(path = "/parsenumber")
    public ResponseEntity<String> parseNumber(@RequestParam(name = "inputNumber") Long number) {
        return baseMethod(number, parser::parseNumberToString);
    }

    @GetMapping(path = "/parsestring")
    public ResponseEntity<String> parseString(@RequestParam(name = "inputString") String input) {
        return baseMethod(input, parser::parseToNumber);
    }

    private <I, O> ResponseEntity<String> baseMethod(I input, Function<I, O> converter) {
        try {
            LOG.debug("input number {}", input);
            O result = converter.apply(input);
            LOG.debug("output result {}", result);
            return ResponseEntity.ok(result.toString());
        } catch (Exception ex) {
            LOG.error("", ex);
            return ResponseEntity.badRequest().body(exceptionWithStackTrace(ex));
        }
    }

    private String exceptionWithStackTrace(Exception ex) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.flush();
        return writer.toString();
    }

}
