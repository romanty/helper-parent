package com.study.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则测试(\w)((?=(\1)(\1)(\1)(\1))+)
 *
 * @author yutong on 2016/12/12.
 */
public class PatternTest {
    public static void main(String[] args) {
        String format = "aaa ffffff 999999999";
//        Pattern pattern = Pattern.compile("(\\w)((?=(\\1)(\\1)(\\1)(\\1))+)");
        Pattern pattern = Pattern.compile("(\\w)(\\1)");
        Matcher matcher = pattern.matcher(format);
        while (matcher.find()) {
            System.out.print(matcher.group() + " | ");
        }
        System.out.println();

        Pattern pattern1 = Pattern.compile("(\\w)(?=\\1\\1\\1)");
        Matcher matcher1 = pattern1.matcher(format);
        while (matcher1.find()) {
            System.out.print(matcher1.group() + " | ");
        }
        System.out.println();

        Pattern pattern2 = Pattern.compile("(\\w)(?=\\1\\1\\1)(\\1)");
        Matcher matcher2 = pattern2.matcher(format);
        while (matcher2.find()) {
            System.out.print(matcher2.group() + " | ");
        }
        System.out.println();

        Pattern pattern3 = Pattern.compile("(\\w)(?=\\1\\1\\1)(\\1)+");
        Matcher matcher3 = pattern3.matcher(format);
        while (matcher3.find()) {
            System.out.print(matcher3.group() + " | ");
        }
        System.out.println();

        Pattern pattern4 = Pattern.compile("(\\w)((?=\\1\\1\\1)(\\1))+");
        Matcher matcher4 = pattern4.matcher(format);
        while (matcher4.find()) {
            System.out.print(matcher4.group() + " | ");
        }
        System.out.println();
    }
}
