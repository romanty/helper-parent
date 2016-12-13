package com.study.android.classloader;

import java.util.Date;

/**
 * @author yutong on 2016/12/2.
 */
public class TestClassLoader {

    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        while (null != classLoader) {
            System.out.println("----> classLoader= " + classLoader);
            classLoader = classLoader.getParent();
        }

        String paymentId= "time-mid-hhss-id-isPart";
        String[] paymentArr = paymentId.split("-");
        paymentId = "time" + paymentArr[3];
        if (paymentArr.length > 4) {
            paymentId += "-" + paymentArr[4];
        }
        System.out.println(paymentId);

        String url = "http://inbornpai-test.img-cn-beijing.aliyuncs.com/11/744dfe4228664a67b299ec43b2c8d834.png";
        try {
            int end = url.contains("?") ? url.indexOf("?") : url.length();
            url = url.substring(url.indexOf(".com/") + 5, end);
        } catch (Exception e) {
            return;
        }

        String mobile = "420984198911095";
        String newMobile = mobile.replaceAll("\\d{13}(?=[\\d\\D]$)", "****************");
        System.out.println(mobile.substring(3, 7));
        System.out.println(newMobile);

    }
}
