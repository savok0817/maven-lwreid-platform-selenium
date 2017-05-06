package com.lwreid.platform.selenium.utils;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nikita Ovsyannikov on 05.10.2016.
 */
public class Func {
    /**
     * Returns first matching group result, if no matches - return @value without changes
     */
    public static final BiFunction<String, String, String> REGEXP_SUBSTRING = (pattern, value) -> {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(value);
        return matcher.find() ? matcher.group(1) : value;
    };
}
