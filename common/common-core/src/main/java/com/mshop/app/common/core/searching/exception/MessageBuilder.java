package com.mshop.app.common.core.searching.exception;

import java.util.Map;

public class MessageBuilder {
    private MessageBuilder() {
        /* This utility class should not be instantiated */
    }

    public static String format(String template, Map<String, Object> params) {
        String result = template;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            result = result.replace(
                    "{" + entry.getKey() + "}",
                    String.valueOf(entry.getValue())
            );
        }
        return result;
    }
}
