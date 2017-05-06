package com.lwreid.platform.selenium.pages.main;

/**
 * Created by Nikita Ovsyannikov on 30.01.2017.
 */
public @interface Reg {
    String FIRST_DIGIT = "^[\\D]+([\\d]+)";
    String LAST_DIGIT = "[\\D\\d\\s]+-[\\s\\D]+([0-9]+)";
    String THEME_CURRENT = "(\\/storage\\/images\\/them(e|es)\\/[0-9\\D]+\\/)images";
    String THEME_PREVIEW = "^[a-z0-9\\D]+(\\/storage\\/images\\/them(e|es)\\/[0-9\\D]+\\/)preview";
}
