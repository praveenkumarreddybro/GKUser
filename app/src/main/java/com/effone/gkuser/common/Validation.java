package com.effone.gkuser.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 11-07-2016.
 */
public class Validation {


    Pattern letter = Pattern.compile("[a-zA-z]");
    Pattern digit = Pattern.compile("[0-9]");
    Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern eight = Pattern.compile(".{8,32}");


    public boolean isValidEmail(String mStrEmail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(mStrEmail);
        return matcher.matches();
    }

    public boolean isValidPassword(String mStrPassword) {
        Matcher hasLetter = letter.matcher(mStrPassword);
        Matcher hasDigit = digit.matcher(mStrPassword);
        Matcher hasEight = eight.matcher(mStrPassword);
        return (hasLetter.find() || hasDigit.find()) &&
                hasEight.matches();
    }
    public String contiansChar(String mStrPassword) {
        String msg = "done";
        Matcher hasLetter = letter.matcher(mStrPassword);
        if (hasLetter.find())
            msg = continasNumber(mStrPassword);
        else
            msg = "nochar";

        return msg;
    }

    private String continasNumber(String mStrPassword) {
        String msg = "done";
        Matcher hasDigit = digit.matcher(mStrPassword);
        if (hasDigit.find())
            msg = lengthDigit(mStrPassword);
        else
            msg = "nonumbers";
        return msg;
    }

    private String lengthDigit(String mStrPassword) {
        String msg = "done";
        Matcher hasEight = eight.matcher(mStrPassword);
        if (hasEight.matches())
            msg = "done";
        else
            msg = "nolength";
        return msg;
    }


}
